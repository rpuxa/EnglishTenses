package ru.rpuxa.englishtenses.view.fragments

import android.animation.Animator
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
import androidx.core.os.bundleOf
import androidx.core.view.doOnNextLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import ru.rpuxa.englishtenses.*
import ru.rpuxa.englishtenses.databinding.BottomMenuCorrectBinding
import ru.rpuxa.englishtenses.databinding.FragmentExerciseBinding
import ru.rpuxa.englishtenses.databinding.MistakeBottomMenuBinding
import ru.rpuxa.englishtenses.model.*
import ru.rpuxa.englishtenses.view.activities.ExerciseActivity
import ru.rpuxa.englishtenses.view.views.BottomMenu
import ru.rpuxa.englishtenses.view.views.DummyView
import ru.rpuxa.englishtenses.view.views.Menus
import ru.rpuxa.englishtenses.viewmodel.ExerciseViewModel
import kotlin.math.abs
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
    private val answersSpacesDummies = ArrayList<DummyView>()
    private val textDummies = ArrayList<DummyView>()


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

        viewModel.result.liveData.observe(viewLifecycleOwner) {result ->
            if (result != null) {
                if (result.correct) {
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
            val answerView = binding.answersField.inflate(R.layout.item_answer) as TextView
            answerView.isInvisible = true
            answerView.text = text
            binding.answersField.addView(answerView)
            val point = Point()
            answerView.onMeasured {
                val dummyView = DummyView(ctx)
                dummyView.width = answerView.width + dummyWidthMargin
                dummyView.height = answerView.height + dummyHeightMargin
                answersDummies += dummyView

                binding.answers.addView(dummyView)
                dummyView.onMeasured {
                    answerView.coordinates = dummyView.coordinates
                    val coordinates = dummyView.coordinates
                    point.set(coordinates.x, coordinates.y)
                    answerView.isVisible = true
                }
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

            val answer = Answer(index, answerView)
            answerView.setOnTouchListener(AnswerTouchListener(answer))
            answers += answer
        }


        var answerSpaceIndex = 0
        viewModel.sentence.text.split(" ")
            .filter { it.isNotBlank() }
            .forEach { text ->
                val textDummy = DummyView(ctx)
                val view = if (text == "%s") {
                    val space = binding.sentence.inflate(R.layout.item_answer_margin) as ViewGroup
                    val dummy = DummyView(ctx)
                    dummy.height = 50
                    dummy.width = spaceDummyMinWidth
                    space.addView(dummy)
                    answerSpaces += AnswerSpace(
                        answerSpaceIndex++,
                        space
                    )
                    answersSpacesDummies += dummy
                    space
                } else {
                    val view = binding.sentence.inflate(R.layout.item_word) as TextView
                    view.text = text
                    view
                }
                binding.sentence.addView(view)
            }
    }


    private fun showCorrectAnswers() {
        answers.forEach { answer ->
            if (answer.spaceIndex != -1 && answer.answerIndex !in viewModel.rightAnswers)
                answer.moveToAnswers(false)
        }
        answers.forEach { answer ->
            val space = viewModel.rightAnswers.indexOf(answer.answerIndex)
            if (space != -1 && answer.spaceIndex != -1 && answer.spaceIndex != space)
                answer.moveToSpace(answerSpaces[space])
        }
        viewModel.rightAnswers.forEachIndexed {  space, answerId ->
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


    private var animator: Animator? = null
    private fun moveAnswersWithDummies(duration: Long, answerIndex: Int, spaceIndex: Int) {
        Log.d(TAG, "moveAnswersWithDummies $answerIndex $spaceIndex")
        answers.forEach { answer ->
            Log.d(TAG, "Check ${answer.answerIndex} ${answer.spaceIndex}")
            if (answer.answerIndex > answerIndex && answer.spaceIndex == -1) {
                val dummyView = answersDummies[answer.answerIndex]
                moveAnswerToDummies(duration, answer, dummyView)
            }
        }
        animator?.cancel()
        animator = ObjectAnimator.ofInt(0)
            .apply {
                this.duration = duration + 200
                addUpdateListener {
                    answers.forEach { answer ->
                        if (answer.spaceIndex > spaceIndex) {
                            val dummyView = answersSpacesDummies[answer.spaceIndex]
                            answer.move(dummyView.coordinates, 0)
                        }
                    }
                }
                start()
            }
    }

    private fun moveAnswerToDummies(duration: Long, answer: Answer, dummyView: DummyView) {
        Log.d(TAG, "Preparing move with dummy  ${answer.answerIndex}")
        answer.view.animation?.cancel()
        dummyView.requestLayout()

        dummyView.doOnNextLayout {
            val coords = dummyView.coordinates - binding.answersField.coordinates

            Log.d(TAG, "Move with dummy  ${answer.answerIndex}  ${dummyView.hashCode()}")
            answer.view.animate()
                .x(coords.x.toFloat())
                .y(coords.y.toFloat())
                .setDuration(duration)
                .start()
        }
    }

    private fun lowestEmptySpace(): AnswerSpace? {
        val set = LinkedHashSet<Int>()
        set.addAll(0 until answersSpacesDummies.size)
        answers.forEach { set -= it.spaceIndex }
        val min = set.min() ?: return null
        return answerSpaces[min]
    }

    private inner class Answer(var answerIndex: Int, val view: View) {
        var spaceIndex by Delegates.observable(-1) { _, old, new ->
            if (old != new) {
                viewModel.updateSpaces(answers())
            }
        }

        fun move(coordinates: Point, duration: Long) {
            Log.d(TAG, "Move $answerIndex to coordinates $coordinates")
            val (x, y) = coordinates - view.parentCoordinates
            if (duration == 0L) {
                view.x = x.toFloat()
                view.y = y.toFloat()
                return
            }
            view.animation?.cancel()
            view.animate()
                .x(x.toFloat())
                .y(y.toFloat())
                .setDuration(duration)
                .start()
        }

        fun moveToSpace(space: AnswerSpace) {
            Log.d(TAG, "Move $answerIndex to space number ${space.index} from $spaceIndex")

            val duration = duration(space.view.coordinates)
            move(space.view.coordinates, duration)
            if (spaceIndex == -1) {
                val dummyView = answersDummies[answerIndex]
                dummyView.width = 0
                moveAnswersWithDummies(duration, answerIndex, space.index)
                dummyView.requestLayout()
            } else {
                answersSpacesDummies[spaceIndex].animateWidth(spaceDummyMinWidth, duration)
            }
            answersSpacesDummies[space.index].animateWidth(view.width, duration)
            val found = answers.find { it.spaceIndex == space.index }
            spaceIndex = space.index
            found?.moveToAnswers(false)
        }

        fun moveToAnswers(animateSpaceDummy: Boolean = true) {
            Log.d(TAG, "Move $answerIndex to answers")
            val dummyView = answersDummies[answerIndex]
            val coords = dummyView.coordinates
            val duration = duration(coords)
            dummyView.width = view.width + dummyWidthMargin
            dummyView.height = view.height + dummyHeightMargin
            val spaceIndex = spaceIndex
            this.spaceIndex = -1
            moveAnswersWithDummies(duration, answerIndex - 1, spaceIndex)
            dummyView.requestLayout()
            if (animateSpaceDummy)
                answersSpacesDummies[spaceIndex].animateWidth(spaceDummyMinWidth, duration)
        }

        fun moveBack() {
            Log.d(TAG, "Move $answerIndex back from $spaceIndex")
            if (spaceIndex == -1) {
                val coords = answersDummies[answerIndex].coordinates
                val duration = duration(coords)
                move(coords, duration)
            } else {
                val space = answerSpaces[spaceIndex]
                val duration = duration(space.view.coordinates)
                move(space.view.coordinates, duration)
            }
        }

        private fun duration(coords: Point) =
            200L

        fun move() {
            Log.d(TAG, "Move $answerIndex to $spaceIndex  (2)")
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
        val view: ViewGroup
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
                        answer.view.elevation = 1f
                        answer.move(
                            Point(
                                event.rawX - answer.view.width / 2,
                                event.rawY - answer.view.height / 2
                            ), 0
                        )
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    answer.view.elevation = 0f
                    if (isAClick()) {
                        answer.move()
                    } else {
                        run checkSpaceInterception@{
                            answerSpaces.forEach { space ->
                                if (space.view.viewRect.contains(
                                        event.rawX.toInt(),
                                        event.rawY.toInt()
                                    )
                                ) {
                                    if (listener.onMoveToSpace(answer.answerIndex, space.index)) {
                                        answer.moveToSpace(space)
                                    } else {
                                        answer.moveBack()
                                    }
                                    return@checkSpaceInterception
                                }
                            }
                            if (answer.spaceIndex != -1) {
                                answer.moveToAnswers()
                            } else {
                                answer.moveBack()
                            }
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