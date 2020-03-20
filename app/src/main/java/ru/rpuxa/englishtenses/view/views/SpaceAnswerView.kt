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
    }

    var defaultWidth = 0

    val root get() = binding.root

    override fun setWidth(width: Int) {
        binding.dummyView.width = width
    }

    override fun setHeight(height: Int) {
        binding.dummyView.height = height
    }

    override fun requestLayout() {
        binding.dummyView.requestLayout()
    }

    var text: String
        get() = binding.text.text.toString()
        set(value) {
            binding.text.text = value
        }
}