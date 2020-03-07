package ru.rpuxa.englishtenses

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnRepeat
import androidx.core.view.*
import ru.rpuxa.englishtenses.databinding.ActivityMainBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var draggingAnswerIndex = -1
    private val answers = ArrayList<Answer>()
    private val answerSpaces = ArrayList<AnswerSpace>()
    private val draggingAnswerView get() = answers[draggingAnswerIndex].view
    private var movingAnswerInSpace = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        answers.clear()
        answerSpaces.clear()

        val text = "Hello. I %s hungry"


        listOf("am", "is", "haveasdfasdf").forEachIndexed { index, it ->
            val textView = binding.answers.inflate(R.layout.item_answer) as TextView
            answers.add(Answer(textView, index))
            textView.text = it

            textView.setOnTouchListener(AnswerTouchListener(index))
            textView.setOnClickListener { v ->
                val answerSpace =
                    answerSpaces.firstOrNull { it.view.isEmpty() } ?: return@setOnClickListener
                val viewCoordinates = IntArray(2)
                val parentCoordinates = IntArray(2)
                val spaceCoordinates = IntArray(2)
                val height = v.height
                val width = v.width
                v.getLocationInWindow(viewCoordinates)
                binding.root.getLocationInWindow(parentCoordinates)
                answerSpace.view.getLocationInWindow(spaceCoordinates)
                v.removeParent()
                val popupWindow = PopupWindow(
                    v,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    resources.getDimensionPixelSize(R.dimen.answer_height)
                )

                val startX = viewCoordinates[0] - parentCoordinates[0]
                val startY = viewCoordinates[1] - parentCoordinates[1] + height
                val endX = spaceCoordinates[0] - parentCoordinates[0]
                val endY = spaceCoordinates[1] - parentCoordinates[1] + height

                popupWindow.showAsDropDown(
                    binding.root,
                    startX,
                    startY
                )

                val dummy = DummyView(this)
                dummy.height = 50
                dummy.isInvisible = true
                answerSpace.view.addView(dummy)
                val animator = ObjectAnimator.ofFloat(
                    0f,
                    1f
                )
                animator.duration = 3000L
                animator.addUpdateListener {
                    val percent = it.animatedValue as Float
                    val w = answerSpace.view.width + percent * (width - answerSpace.view.width)
                    dummy.width = w.toInt()
                    dummy.requestLayout()
                    val x = startX + percent * (endX - startX)
                    val y = startY + percent * (endY - startY)
                    popupWindow.update(
                        binding.root,
                        x.toInt(),
                        y.toInt(),
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        resources.getDimensionPixelSize(R.dimen.answer_height)
                    )
                }
                animator.start()
            }

            binding.answers.addView(textView)
        }

        var answerSpaceIndex = 0
        text.split(" ")
            .filter { it.isNotBlank() }
            .forEachIndexed { index, it ->
                val view = if (it == "%s") {
                    val view = binding.sentence.inflate(R.layout.item_answer_margin) as ViewGroup
                    answerSpaces.add(
                        AnswerSpace(
                            view,
                            answerSpaceIndex++
                        )
                    )
                    view.setOnDragListener { _, event ->
                        when (event.action) {
                            DragEvent.ACTION_DRAG_STARTED -> {
                                draggingAnswerView.isInvisible = true
                                movingAnswerInSpace
                            }
                            DragEvent.ACTION_DRAG_ENTERED -> true
                            DragEvent.ACTION_DROP -> {
                                draggingAnswerView.removeParent()
                                view.addView(draggingAnswerView)
                                draggingAnswerView.isVisible = true
                                true
                            }
                            DragEvent.ACTION_DRAG_ENDED -> {
                                if (!event.result) {
                                    draggingAnswerView.isVisible = true
                                }
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    }
                    view
                } else {
                    val view = binding.sentence.inflate(R.layout.item_word) as TextView
                    view.text = it
                    view
                }

                binding.sentence.addView(view)
            }

        binding.answers.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> !movingAnswerInSpace
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DROP -> {
                    draggingAnswerView.removeParent()
                    binding.answers.addView(draggingAnswerView)
                    draggingAnswerView.isVisible = true
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (!event.result) {
                        draggingAnswerView.isVisible = true
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


    private class MyDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {
        override fun onDrawShadow(canvas: Canvas) {
            view.draw(canvas)
        }
    }

    private inner class AnswerSpace(
        val view: ViewGroup,
        val index: Int
    )

    private inner class Answer(
        val view: View,
        val index: Int
    )

    private inner class AnswerTouchListener(private val index: Int) : View.OnTouchListener {
        private var startX = 0f
        private var startY = 0f

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!isAClick(startX, event.x, startY, event.y)) {
                        println("drag started")
                        @Suppress("DEPRECATION")
                        v.startDrag(
                            null,
                            MyDragShadowBuilder(v),
                            v,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) View.DRAG_FLAG_OPAQUE else 0
                        )
                        draggingAnswerIndex = index
                        movingAnswerInSpace = v in binding.answers
                        false
                    } else {
                        true
                    }
                }

                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val endY = event.y
                    if (isAClick(startX, endX, startY, endY)) {
                        v.performClick()
                    }
                    false
                }
                else -> false
            }
        }

        private fun isAClick(
            startX: Float,
            endX: Float,
            startY: Float,
            endY: Float
        ): Boolean = abs(startX - endX) <= 20 && abs(startY - endY) <= 20
    }
}
