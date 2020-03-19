package ru.rpuxa.englishtenses.view.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.minus
import androidx.core.graphics.plus
import androidx.core.os.bundleOf
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import ru.rpuxa.englishtenses.*
import ru.rpuxa.englishtenses.databinding.BottomMenuCorrectBinding
import ru.rpuxa.englishtenses.databinding.FragmentExerciseBinding
import ru.rpuxa.englishtenses.databinding.MistakeBottomMenuBinding
import ru.rpuxa.englishtenses.model.*
import ru.rpuxa.englishtenses.view.activities.ExerciseActivity
import ru.rpuxa.englishtenses.view.activities.IrregularVerbsActivity
import ru.rpuxa.englishtenses.view.views.*
import ru.rpuxa.englishtenses.viewmodel.ExerciseViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin
import kotlin.properties.Delegates

class ExerciseFragment : Fragment() {


    private val tenses by lazy { arguments?.get(ExerciseActivity.TENSES) as Set<Int> }
    private val tipsEnabled by lazy { arguments?.get(TIPS_ENABLED) as Boolean }

    private val binding by lazy { FragmentExerciseBinding.inflate(layoutInflater) }


    private val viewModel: ExerciseViewModel by fragmentViewModel()

    private val dummyWidthMargin = 16.dpToPx()
    private val dummyHeightMargin = 8.dpToPx()
    private val spaceDummyMinWidth by lazy { resources.getDimensionPixelSize(R.dimen.space_answer_min_width) }

    private val answers = ArrayList<Answer>()
    private val answerSpaces = ArrayList<AnswerSpace>()
    private val answersDummies = ArrayList<DummyView>()
    private val spaceAnswerDummies = ArrayList<DummyView>()


    private val listener by lazy { viewModel.answerListener }
    private var onNext: ((ExerciseResult) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(unused: View, savedInstanceState: Bundle?) {
        viewModel.load(tenses, tipsEnabled)

        binding.tip.setOnClickListener {
            val space = lowestEmptySpace()
            if (space != null) {
                viewModel.tipModeOn(space.index)
            } else {
                check()
            }
        }

        binding.irregularVerbs.setOnClickListener {
            viewModel.irregularVerbsClick()
        }

        viewModel.showIrregularVerbTable.observe(viewLifecycleOwner) {
            startActivity<IrregularVerbsActivity>(
                IrregularVerbsActivity.SEARCH_QUERY to it
            )
        }

        var tipMenu: BottomMenu? = null
        viewModel.tipMode.observe(viewLifecycleOwner) {
            if (it == ExerciseViewModel.TIP_MODE_OFF) {
                tipMenu?.dismiss()
            } else {
                tipMenu = Menus.showTipMenu(act, it.tense, it.spaceIndex, answerSpaces.size > 1)
            }
        }

        binding.check.setOnClickListener {
            check()
        }

        viewModel.showTipButton.observe(viewLifecycleOwner) {
            binding.tip.isVisible = it
            binding.check.isGone = it
        }

        viewModel.result.liveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                if (result.allCorrect) {
                    val binding = BottomMenuCorrectBinding.inflate(act.layoutInflater)
                    val menu = BottomMenu(binding.root)
                    binding.next.setOnClickListener {
                        menu.dismiss()
                        onNext?.invoke(result)
                    }
                    menu.show(act)
                } else {
                    val binding = MistakeBottomMenuBinding.inflate(act.layoutInflater)
                    val menu = BottomMenu(binding.root)
                    binding.next.setOnClickListener {
                        menu.dismiss()
                        onNext?.invoke(result)
                    }
                    binding.showCorrectAnswers.setOnClickListener {
                        showCorrectAnswers()
                        it.isVisible = false
                    }
                    menu.show(act)
                }
            }
        }

        viewModel.shuffledAnswers.forEachIndexed { index, text ->
            val answerView = binding.field.inflate(R.layout.item_answer) as TextView
            answerView.elevation = 0.01f
            answerView.text = text
            binding.field.addView(answerView)
            val flyView = FlyView(answerView, null)
            answerView.onMeasured {
                val dummyView = DummyView(ctx)
                dummyView.width = answerView.width + dummyWidthMargin
                dummyView.height = answerView.height + dummyHeightMargin
                flyView.follow(dummyView)
                answersDummies += dummyView
                binding.answers.addView(dummyView)
            }

            viewModel.getAnswerState(index).observe(viewLifecycleOwner) {
                val tint = when (it) {
                    is Result -> if (it.correct) R.color.colorRightAnswer else R.color.colorWrongAnswer
                    WrongSignal -> {
                        viewModel.resetState(index)
                        ObjectAnimator.ofInt(1, 0, 1, 0, 1, 0).apply {
                            duration = 400
                            addUpdateListener {
                                val color = it.animatedValue as Int

                                answerView.backgroundTintList = ContextCompat.getColorStateList(
                                    ctx,
                                    if (color == 1) R.color.colorWrongAnswer else android.R.color.white
                                )
                            }
                            start()
                        }

                        null
                    }
                    is None -> android.R.color.white
                }

                if (tint != null)
                    answerView.backgroundTintList = ContextCompat.getColorStateList(ctx, tint)
            }

            val answer = Answer(index, answerView, flyView)
            answerView.setOnTouchListener(AnswerTouchListener(answer))
            answers += answer
        }


        var answerSpaceIndex = 0
        viewModel.sentence.items
            .forEach { item ->
                val itemDummy = DummyView(ctx)
                val (view, resizeable) = when (item) {
                    is WordAnswer -> {
                        spaceAnswerDummies += itemDummy
                        val spaceAnswerView = SpaceAnswerView(layoutInflater)
                        spaceAnswerView.setWidth(spaceDummyMinWidth)
                        spaceAnswerView.setText(item.infinitive)
                        answerSpaces += AnswerSpace(
                            answerSpaceIndex++,
                            spaceAnswerView
                        )
                        spaceAnswerView.root to spaceAnswerView
                    }
                    is Word -> {
                        val view = binding.sentence.inflate(R.layout.item_word) as TextView
                        view.text = item.text
                        view to null
                    }
                }
                binding.field.addView(view)
                view.onMeasured {
                    itemDummy.width = view.width
                    itemDummy.height = view.height
                    resizeable?.defaultWidth = view.width.coerceAtLeast(spaceDummyMinWidth)
                    binding.sentence.addView(itemDummy)
                    if (resizeable != null) {
                        binding.sentence.addView(
                            DummyView(ctx).apply {
                                width = 8.dpToPx()
                            }
                        )
                    }
                    itemDummy.onMeasured {
                        FlyView(view, resizeable).follow(itemDummy)
                    }
                }
            }
    }


    private fun showCorrectAnswers() {
        answers.forEach { answer ->
            if (answer.spaceIndex != -1 && answer.answerIndex !in viewModel.rightAnswers)
                answer.moveToAnswers()
        }
        answers.forEach { answer ->
            val space = viewModel.rightAnswers.indexOf(answer.answerIndex)
            if (space != -1 && answer.spaceIndex != -1 && answer.spaceIndex != space)
                answer.moveToSpace(answerSpaces[space])
        }
        viewModel.rightAnswers.forEachIndexed { space, answerId ->
            val answer = answers[answerId]
            if (answer.spaceIndex != space)
                answer.moveToSpace(answerSpaces[space])
        }
        viewModel.setAllCorrect()
    }

    fun setOnNextListener(block: (ExerciseResult) -> Unit) {
        onNext = block
    }

    private fun check() {
        viewModel.check(answers())
    }

    private fun answers(): List<Int?> =
        List(answerSpaces.size) { spaceIndex ->
            answers.find { it.spaceIndex == spaceIndex }?.answerIndex
        }

    private fun lowestEmptySpace(): AnswerSpace? {
        val set = LinkedHashSet<Int>()
        set.addAll(0 until spaceAnswerDummies.size)
        answers.forEach { set -= it.spaceIndex }
        val min = set.min() ?: return null
        return answerSpaces[min]
    }

    private inner class Answer(
        var answerIndex: Int,
        val view: View,
        val flyView: FlyView
    ) {
        var spaceIndex by Delegates.observable(-1) { _, old, new ->
            if (old != new) {
                viewModel.updateSpaces(answers())
            }
        }

        fun freeMove(coordinates: Point) {
            flyView.stopFollowing()
            Log.d(TAG, "Move $answerIndex to coordinates $coordinates")
            var (x, y) = coordinates - view.parentCoordinates
            val parent = view.parent as View
            x = x.coerceAtLeast(0).coerceAtMost(parent.width - view.width)
            y = y.coerceAtLeast(0).coerceAtMost(parent.height - view.height)
            view.x = x.toFloat()
            view.y = y.toFloat()
        }

        fun moveToSpace(space: AnswerSpace) {
            Log.d(TAG, "Move $answerIndex to space number ${space.index} from $spaceIndex")
            answers.find { it.spaceIndex == space.index }?.moveToAnswers()
            val dummyView = spaceAnswerDummies[space.index]
            dummyView.apply {
                width = view.width
                requestLayout()
            }
            flyView.follow(dummyView)
            answersDummies[answerIndex].apply {
                width = 0
                requestLayout()
            }
            spaceIndex = space.index
        }

        fun moveToAnswers() {
            Log.d(TAG, "Move $answerIndex to answers")
            val dummyView = answersDummies[answerIndex]
            dummyView.width = view.width + dummyWidthMargin
            dummyView.requestLayout()
            flyView.follow(dummyView)
            if (spaceIndex != -1) {
                val spaceDummyView = spaceAnswerDummies[spaceIndex]
                spaceDummyView.width = answerSpaces[spaceIndex].view.defaultWidth
                spaceDummyView.requestLayout()
            }
            spaceIndex = -1
        }

        fun moveToEmpty() {
            if (spaceIndex == -1) {
                val space = lowestEmptySpace()
                if (space != null && listener.onMoveToSpace(answerIndex, space.index))
                    moveToSpace(space)
            } else {
                moveToAnswers()
            }
        }
    }

    private class AnswerSpace(
        val index: Int,
        val view: SpaceAnswerView
    )

    private inner class AnswerTouchListener(
        private val answer: Answer
    ) : View.OnTouchListener {
        private var startX = 0f
        private var startY = 0f

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val duration = event.eventTime - event.downTime

            fun isAClick(): Boolean =
                duration < 500 && abs(startX - event.rawX) <= 20 && abs(startY - event.rawY) <= 20

            return when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    startY = event.rawY
                    listener.onTouch(answer.answerIndex)
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!isAClick()) {
                        answer.view.elevation = 0.02f
                        answer.freeMove(
                            Point(
                                event.rawX - answer.view.width / 2,
                                event.rawY - answer.view.height / 2
                            )
                        )
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    answer.view.elevation = 0.01f
                    if (isAClick()) {
                        answer.moveToEmpty()
                    } else {
                        run checkSpaceInterception@{
                            answerSpaces.forEach { space ->
                                if (space.view.root.viewRect.contains(
                                        event.rawX.toInt(),
                                        event.rawY.toInt()
                                    )
                                ) {
                                    if (listener.onMoveToSpace(answer.answerIndex, space.index)) {
                                        answer.moveToSpace(space)
                                    } else {
                                        answer.moveToAnswers()
                                    }
                                    return@checkSpaceInterception
                                }
                            }
                            answer.moveToAnswers()
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }


    interface AnswersMoveListener {
        fun onMoveToSpace(answerId: Int, spaceId: Int): Boolean
        fun onTouch(id: Int): Boolean
    }

    inner class FlyView(
        private val view: View,
        private val resizableView: ResizableView?
    ) {

        private var dummyView: DummyView? = null
        private var listener: DummyView.FollowListener? = null
        private lateinit var startPos: Point
        private lateinit var startDimension: Point

        fun follow(dummy: DummyView) {
            stopFollowing()
            dummyView = dummy
            val listener = object : DummyView.FollowListener(dummy, lifecycleScope) {
                override suspend fun onMove(
                    dummyView: DummyView,
                    percent: Double,
                    updateStartPos: Boolean
                ) {
                    withContext(Dispatchers.Main) {
                        // interpolator function
                        val f = sin((percent * 2 - 1) * PI / 2)
                        val percent = (f + 1) / 2
                        val dummyViewCoordinates = dummyView.coordinates
                        val dummyViewDimension = Point(dummyView.width, dummyView.height)
                        if (updateStartPos) {
                            startPos = view.coordinates
                            startDimension = Point(view.width, view.height)
                        }
                        val newCoordinates = startPos + (dummyViewCoordinates - startPos) * percent
                        val newDimension =
                            startDimension + (dummyViewDimension - startDimension) * percent
                        if (isActive) {
                            resizableView?.apply {
                                setWidth(newDimension.x)
                                setHeight(newDimension.y)
                                if (this !== view) {
                                    requestLayout()
                                }
                            }
                            view.coordinates = newCoordinates
                            view.requestLayout()
                        }
                        suspendCoroutine<Unit> { continuation ->
                            view.doOnLayout {
                                continuation.resume(Unit)
                            }
                        }
                    }
                }
            }
            this.listener = listener
            dummy.follow(listener)
        }

        fun stopFollowing() {
            listener?.let { dummyView?.stopFollowing(it) }
        }
    }


    companion object {
        const val TIPS_ENABLED = "tips"

        fun create(tenses: Set<Int>, tipEnabled: Boolean) = ExerciseFragment().apply {
            arguments = bundleOf(
                ExerciseActivity.TENSES to tenses,
                TIPS_ENABLED to tipEnabled
            )
        }

        val TAG = ExerciseFragment::class.simpleName + "Debug"
    }
}