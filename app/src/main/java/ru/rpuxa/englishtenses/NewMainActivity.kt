package ru.rpuxa.englishtenses

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.contains
import androidx.core.graphics.minus
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import ru.rpuxa.englishtenses.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.sqrt

class NewMainActivity : AppCompatActivity() {

    private val answersTexts =
        listOf("am", "is", "very long word", "another", "and another", "and another another")
    private val sentence =
        "This is simple sentence. And this %s space for answer. Another space: %s"
    private val dummyWidthMargin = 16.dpToPx()
    private val dummyHeightMargin = 8.dpToPx()
    private val spaceDummyMinWidth by lazy { resources.getDimensionPixelSize(R.dimen.space_answer_min_width) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val answers = ArrayList<Answer>()
    private val answerSpaces = ArrayList<AnswerSpace>()
    private val answersDummies = ArrayList<DummyView>()
    private val answersSpacesDummies = ArrayList<DummyView>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        answersTexts.forEachIndexed { index, text ->
            val answerView = binding.answersField.inflate(R.layout.item_answer) as TextView
            answerView.isInvisible = true
            answerView.text = text
            binding.answersField.addView(answerView)
            val point = Point()
            answerView.onMeasured {
                val dummyView = DummyView(this)
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
            val answer = Answer(index, answerView)
            answerView.setOnTouchListener(AnswerTouchListener(answer))
            answers += answer
        }


        var answerSpaceIndex = 0
        sentence.split(" ")
            .filter { it.isNotBlank() }
            .forEach { text ->
                val view = if (text == "%s") {
                    val space = binding.sentence.inflate(R.layout.item_answer_margin) as ViewGroup
                    val dummy = DummyView(this)
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

    private var animator: Animator? = null
    private fun moveAnswersWithDummies(duration: Long, answerIndex: Int, spaceIndex: Int) {
        answers.forEach { answer ->
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
        answer.view.animation?.cancel()
        dummyView.doOnNextLayout {
            val coords = dummyView.coordinates - binding.answersField.coordinates

            answer.view.animate()
                .x(coords.x.toFloat())
                .y(coords.y.toFloat())
                .setDuration(duration)
                .start()
        }
    }

    private var View.coordinates: Point
        get() {
            val array = IntArray(2)
            getLocationOnScreen(array)
            return Point(array[0], array[1])
        }
        set(point) {
            val (x, y) = point - parentCoordinates
            setX(x.toFloat())
            setY(y.toFloat())
        }

    private val View.parentCoordinates: Point get() = (parent as View).coordinates

    private val View.viewRect: Rect
        get() {
            val (x, y) = coordinates
            return Rect(x, y, x + width, y + height)
        }

    private inner class Answer(var answerIndex: Int, val view: View) {
        var spaceIndex = -1

        fun move(coordinates: Point, duration: Long) {
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
            println("MOVE $answerIndex TO SPACE")

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

        fun moveToAnswers(changeSpace: Boolean = true) {
            println("MOVE $answerIndex TO ANSWERS")
            val dummyView = answersDummies[answerIndex]
            val coords = dummyView.coordinates
            val duration = duration(coords)
            dummyView.width = view.width + dummyWidthMargin
            dummyView.height = view.height + dummyHeightMargin
            val spaceIndex = spaceIndex
            this.spaceIndex = -1
            moveAnswersWithDummies(duration, answerIndex - 1, spaceIndex)
            dummyView.requestLayout()
            if (changeSpace)
                answersSpacesDummies[spaceIndex].animateWidth(spaceDummyMinWidth, duration)
        }

        fun moveBack() {
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
            if (spaceIndex == -1) {
                val set = LinkedHashSet<Int>()
                set.addAll(0 until answersSpacesDummies.size)
                answers.forEach { set -= it.spaceIndex }
                val min = set.min()
                if (min != null)
                    moveToSpace(answerSpaces[min])
            } else {
                moveToAnswers()
            }
        }
    }

    private class AnswerSpace(
        val index: Int,
        val view: ViewGroup
    )

    private inner class AnswerTouchListener(private val answer: Answer) : View.OnTouchListener {
        private var startX = 0f
        private var startY = 0f

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val duration = event.eventTime - event.downTime

            fun isAClick(): Boolean =
                duration < 500 && abs(startX - event.x) <= 20 && abs(startY - event.y) <= 20

            return when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    true
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
                                    answer.moveToSpace(space)
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
}
