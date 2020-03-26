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
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    var defaultWidth = 0

    val root get() = binding.root

    val dummy get() = binding.dummyView

    override fun setWidth(width: Int) {
        dummy.width = width
    }

    override fun setHeight(height: Int) {
        dummy.height = height
    }

    override fun requestLayout() {
        dummy.requestLayout()
    }

    var text: String
        get() = binding.text.text.toString()
        set(value) {
            binding.text.text = value
        }
}