package ru.rpuxa.englishtenses

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import ru.rpuxa.englishtenses.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var draggingView: TextView? = null
    private var spaceView: ViewGroup? = null
    private var inAnswers = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val text = "Hello. I %s hungry"
        val answers = listOf("am", "is", "have")



        answers.forEach {
            val textView = binding.answers.inflate(R.layout.item_answer) as TextView
            textView.text = it

            textView.setOnTouchListener { v, event ->
                @Suppress("DEPRECATION")
                v.startDrag(
                    null,
                    MyDragShadowBuilder(v),
                    v,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) View.DRAG_FLAG_OPAQUE else 0
                )
                draggingView = v as TextView
                inAnswers = v in binding.answers
                true
            }

            binding.answers.addView(textView)
        }

        text.split(" ").filter { it.isNotBlank() }
            .forEachIndexed { index, it ->
                val view = if (it == "%s") {
                    val view = binding.sentence.inflate(R.layout.item_answer_margin) as ViewGroup
                    view.setOnDragListener { _, event ->
                        when (event.action) {
                            DragEvent.ACTION_DRAG_STARTED -> {
                                draggingView!!.isInvisible = true
                                inAnswers
                            }
                            DragEvent.ACTION_DRAG_ENTERED -> true
                            DragEvent.ACTION_DROP -> {
                                draggingView!!.removeParent()
                                view.addView(draggingView)
                                draggingView!!.isVisible = true
                                true
                            }
                            DragEvent.ACTION_DRAG_ENDED -> {
                                if (!event.result) {
                                    draggingView!!.isVisible = true
                                    draggingView = null
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
                DragEvent.ACTION_DRAG_STARTED -> !inAnswers
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DROP -> {
                    draggingView!!.removeParent()
                    binding.answers.addView(draggingView)
                    draggingView!!.isVisible = true
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (!event.result) {
                        draggingView!!.isVisible = true
                        draggingView = null
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


    class MyDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {
        override fun onDrawShadow(canvas: Canvas) {
            view.draw(canvas)
        }

    }
}
