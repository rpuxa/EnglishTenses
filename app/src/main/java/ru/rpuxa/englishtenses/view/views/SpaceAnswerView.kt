package ru.rpuxa.englishtenses.view.views

import android.view.LayoutInflater
import android.widget.FrameLayout
import org.jetbrains.anko.layoutInflater
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.SpaceAnswerBinding

class SpaceAnswerView(layoutInflater: LayoutInflater) : ResizableView {
    private val binding = SpaceAnswerBinding.inflate(layoutInflater)
    init {
        binding.root.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            binding.root.resources.getDimensionPixelSize(R.dimen.answer_height)
        )
     /*   binding.text.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )*/
    }

    var defaultWidth = 0

    val root get() = binding.root

    override fun setWidth(width: Int) {
        binding.dummyView.width = width
//        binding.text.width = width
    }

    override fun setHeight(height: Int) {
        binding.dummyView.height = height
    }

    override fun requestLayout() {
        binding.dummyView.requestLayout()
//        binding.text.requestLayout()
    }

    fun setText(s: String) {
        binding.text.text = s
    }
}