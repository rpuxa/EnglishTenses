package english.tenses.practice.view.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
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
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import org.jetbrains.anko.support.v4.*
import english.tenses.practice.*
import english.tenses.practice.databinding.BottomMenuCorrectBinding
import english.tenses.practice.databinding.FragmentExerciseBinding
import english.tenses.practice.databinding.MistakeBottomMenuBinding
import english.tenses.practice.model.*
import english.tenses.practice.view.activities.ExerciseActivity
import english.tenses.practice.view.activities.IrregularVerbsActivity
import english.tenses.practice.view.views.*
import english.tenses.practice.viewmodel.ExerciseViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin
import kotlin.properties.Delegates

class ExerciseFragment : Fragment() {


    private val tenses by lazy { arguments?.get(ExerciseActivity.TENSES) as Set<Int> }
    private val tipsEnabled by lazy { arguments?.get(TIPS_ENABLED) as Boolean }
    private val dimensions by lazy { Dimensions() }
    private val binding by lazy { FragmentExerciseBinding.inflate(layoutInflater) }
    private val viewModel: ExerciseViewModel by fragmentViewModel()
    private val answers = ArrayList<Answer>()
    private val answerSpaces = ArrayList<AnswerSpace>()
    private val answersDummies = ArrayList<DummyView>()
    private val spaceAnswerDummies = ArrayList<DummyView>()
    private val wordViews = ArrayList<TextView>()

    private val spaceViewDummies = ArrayList<DummyView>()
    private val wordDummies = ArrayList<DummyView>()


    private val answersDummyParent = object : DummyView.Parent {
        override fun addOnLayoutChangeListener(followListener: DummyView.FollowListener) {
            binding.answers.addOnLayoutChangeListener(followListener)
        }

        override fun removeOnLayoutChangeListener(followListener: DummyView.FollowListener) {
            binding.answers.removeOnLayoutChangeListener(followListener)
        }
    }


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

        binding.sentence.layoutParams.apply {
            this as ViewGroup.MarginLayoutParams
            topMargin = dimensions.layoutMargin
            binding.sentence.layoutParams = this
        }
        binding.answers.layoutParams.apply {
            this as ViewGroup.MarginLayoutParams
            topMargin = dimensions.layoutMargin
            binding.answers.layoutParams = this
        }

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

        viewModel.tipMode.observe(viewLifecycleOwner) {
            if (it == ExerciseViewModel.TIP_MODE_OFF) {
               dismissMenu()
            } else {
                showMenu(
                    Menus.showTipMenu(act, it.tense, it.spaceIndex, answerSpaces.size > 1)
                )
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
                    binding.next.setOnClickListener {
                        dismissMenu()
                        onNext?.invoke(result)
                    }
                    binding.complaint.setOnClickListener {
                        showComplaint()
                    }
                    showMenu(BottomMenu(binding.root))
                } else {
                    val binding = MistakeBottomMenuBinding.inflate(act.layoutInflater)
                    binding.next.setOnClickListener {
                        dismissMenu()
                        onNext?.invoke(result)
                    }
                    binding.showCorrectAnswers.setOnClickListener {
                        showCorrectAnswers()
                        it.isVisible = false
                    }
                    binding.complaint.setOnClickListener {
                        showComplaint()
                    }
                    showMenu(BottomMenu(binding.root))
                }
            }
        }

        viewModel.shuffledAnswers.forEachIndexed { index, text ->
            val answerView = binding.field.inflate(R.layout.item_answer) as TextView
            answerView.isInvisible = true
            answerView.elevation = 0.01f
            answerView.text = text
            binding.field.addView(answerView)
            val flyView = FlyView(answerView, null)
            val dummyView = DummyView(ctx)
            dummyView.dummyParent = answersDummyParent
            answersDummies += dummyView
            binding.answers.addView(dummyView)

            viewModel.getAnswerState(index).observe(viewLifecycleOwner) {
                val tint = when (it) {
                    is Result -> if (it.correct) R.color.colorRightAnswer else R.color.colorWrongAnswer
                    WrongSignal -> {
                        viewModel.resetState(index)
                        ObjectAnimator.ofInt(1, 0, 1, 0, 1, 0).apply {
                            duration = 400
                            addUpdateListener {
                                val color = it.animatedValue as Int

                                answerView.backgroundTintList =
                                    ContextCompat.getColorStateList(
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
                    answerView.backgroundTintList =
                        ContextCompat.getColorStateList(ctx, tint)
            }

            val answer = Answer(index, answerView, flyView)
            answerView.setOnTouchListener(AnswerTouchListener(answer))
            answers += answer
        }

        var answerSpaceIndex = 0
        viewModel.sentence.items.forEach { item ->
            val itemDummy = DummyView(ctx)
            val (view, resizeable) = when (item) {
                is WordAnswer -> {
                    spaceAnswerDummies += itemDummy
                    val spaceAnswerView = SpaceAnswerView(layoutInflater)
                    spaceAnswerView.text = item.infinitive
                    answerSpaces += AnswerSpace(
                        answerSpaceIndex++,
                        spaceAnswerView
                    )
                    spaceViewDummies += itemDummy
                    spaceAnswerView.root to spaceAnswerView
                }
                is Word -> {
                    val view = binding.sentence.inflate(R.layout.item_word) as TextView
                    view.text = item.text
                    wordViews += view
                    wordDummies += itemDummy
                    view to null
                }
            }
            binding.field.addView(view)
            binding.sentence.addView(itemDummy)
            if (resizeable != null) {
                binding.sentence.addView(
                    DummyView(ctx).apply {
                        width = dimensions.wordsMargin
                    }
                )
            }
            view.isInvisible = true
        }

        lifecycleScope.launch {
            while (isActive) {


                var countToWait = 0
                val channel = Channel<Unit>(Channel.UNLIMITED)

                answers.forEach {
                    it.view.updateParams(height = dimensions.spaceHeight)
                    it.view.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimensions.answerTextSize)

                    countToWait++
                    it.view.requestLayout()
                    it.view.onMeasured {
                        val dummyView = answersDummies[it.answerIndex]
                        dummyView.width = it.view.width + dimensions.dummyWidthMargin
                        dummyView.height = it.view.height + dimensions.dummyHeightMargin
                        dummyView.requestLayout()
                        dummyView.onMeasured {
                            channel.sendBlocking(Unit)
                        }
                    }
                }

                answerSpaces.forEach { answerSpace ->
                    answerSpace.view.setWidth(dimensions.spaceDummyMinWidth)
                    answerSpace.view.setHeight(dimensions.spaceHeight)
                    countToWait++
                    val itemDummy = spaceViewDummies[answerSpace.index]
                    val root = answerSpace.view.root
                    answerSpace.view.requestLayout()
                    root.doOnLayout {
                        itemDummy.width = root.width
                        itemDummy.height = root.height
                        answerSpace.view.defaultWidth = root.width.coerceAtLeast(
                            dimensions.spaceDummyMinWidth
                        )
                        itemDummy.requestLayout()
                        itemDummy.onMeasured {
                            channel.sendBlocking(Unit)
                        }
                    }
                    root.requestLayout()
                }

                wordViews.forEachIndexed { index, view ->
                    view.updateParams(height = dimensions.spaceHeight)
                    view.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        dimensions.wordTextSize
                    )
                    countToWait++
                    val itemDummy = wordDummies[index]
                    view.requestLayout()
                    view.onMeasured {
                        itemDummy.width = view.width
                        itemDummy.height = view.height + dimensions.dummyHeightMargin
                        itemDummy.requestLayout()
                        itemDummy.onMeasured {
                            channel.sendBlocking(Unit)
                        }
                    }
                }

                withContext(Dispatchers.Default) {
                    repeat(countToWait) {
                        channel.receive()
                    }
                }

                val currentY = binding.answers.coordinates.y + binding.answers.height
                val maxY = ((binding.irregularVerbs.coordinates.y) * .85).toInt()

                if (currentY < maxY) {
                    answerSpaces.forEach {
                        val itemDummy = spaceViewDummies[it.index]
                        FlyView(it.view.root, it.view).follow(itemDummy)
                        it.view.root.isVisible = true
                    }
                    wordViews.forEachIndexed { index, view ->
                        val itemDummy = wordDummies[index]
                        FlyView(view, null).follow(itemDummy)
                        view.isVisible = true
                    }
                    answers.forEach {
                        it.flyView.follow(answersDummies[it.answerIndex])
                        it.view.isVisible = true
                    }
                    break
                }

                dimensions.scale *= .9f
            }
        }
    }

    private var menu: BottomMenu? = null
    fun showMenu(menu: BottomMenu) {
        this.menu = menu
        menu.show(act)
    }

    fun dismissMenu() {
        menu?.dismiss()
    }

    private fun showComplaint() {
        alert {
            titleResource = R.string.complaint_dialog_title
            messageResource = R.string.complaint_dialog_message

            positiveButton(R.string.send) {
                viewModel.sendComplaint()
                longToast(R.string.complaint_thanks)
                it.dismiss()
            }
            negativeButton(R.string.cancel) {
                it.dismiss()
            }
        }.show()
    }

    private fun showCorrectAnswers() {
        val ids = viewModel.setAllCorrect()

        answers.forEach { answer ->
            val spaceIndex = ids.indexOf(answer.answerIndex)
            if (spaceIndex != -1) {
                answer.moveToSpace(answerSpaces[spaceIndex])
            }
        }
    }

    fun setOnNextListener(block: (ExerciseResult) -> Unit) {
        onNext = block
    }

    private fun check() {
        viewModel.check(answers())
    }

    private fun answers(): List<SpaceState> =
        List(answerSpaces.size) { spaceIndex ->
            val answer = answers.find { it.spaceIndex == spaceIndex }
            if (answer == null) {
                SpaceState(spaceIndex)
            } else {
                SpaceState(spaceIndex, answer.answerIndex, answer.view.text.toString())
            }
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
        val view: TextView,
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

            if (spaceIndex != -1) {
                val spaceDummyView = spaceAnswerDummies[spaceIndex]
                spaceDummyView.width = answerSpaces[spaceIndex].view.defaultWidth
                spaceDummyView.requestLayout()
            }

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
            dummyView.width = view.width + dimensions.dummyWidthMargin
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


    class SpaceState(val spaceId: Int) {
        var empty = true
        var answerId = 0
        lateinit var text: String

        constructor(spaceId: Int, answerId: Int, text: String) : this(spaceId) {
            this.answerId = answerId
            this.text = text
            empty = false
        }
    }

    inner class Dimensions(var scale: Float = 1f) {
        private val layoutMarginDefault =
            resources.getDimensionPixelSize(R.dimen.exercise_layouts_margin)
        private val dummyWidthMarginDefault = 16.dpToPx()
        private val dummyHeightMarginDefault = 8.dpToPx()
        private val spaceDummyMinWidthDefault =
            resources.getDimensionPixelSize(R.dimen.space_answer_min_width)
        private val answerTextSizeDefault = resources.getDimension(R.dimen.default_answer_size)
        private val spaceHeightDefault = resources.getDimensionPixelSize(R.dimen.answer_height)
        private val wordTextSizeDefault = resources.getDimension(R.dimen.word_text_size)
        private val wordsMarginDefault = 8.dpToPx()
        private val answerInnerPaddingDefault =
            resources.getDimensionPixelSize(R.dimen.answer_inner_padding)


        val layoutMargin get() = (scale * layoutMarginDefault).toInt()
        val dummyWidthMargin get() = (scale * dummyWidthMarginDefault).toInt()
        val dummyHeightMargin get() = (scale * dummyHeightMarginDefault).toInt()
        val spaceDummyMinWidth get() = (scale * spaceDummyMinWidthDefault).toInt()
        val answerTextSize get() = scale * answerTextSizeDefault
        val wordTextSize get() = scale * wordTextSizeDefault
        val spaceHeight get() = (scale * spaceHeightDefault).toInt()
        val wordsMargin get() = (scale * wordsMarginDefault).toInt()
        val answerInnerPadding get() = (scale * answerInnerPaddingDefault).toInt()
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