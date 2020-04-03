package english.tenses.practice.view.views

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import english.tenses.practice.R
import english.tenses.practice.databinding.TranslateWindowBinding
import english.tenses.practice.updateParams

class TranslateView(context: Context) : ResizableView {

    private val binding = TranslateWindowBinding.inflate(LayoutInflater.from(context))

    init {
        loading()
        collapse()
        val height = context.resources.getDimensionPixelSize(R.dimen.button3d_height)
        binding.root.layoutParams = ConstraintLayout.LayoutParams(height, height)

        binding.close.setOnClickListener {
            if (opened) {
                onCollapse?.invoke()
                opened = false
            }
        }

        binding.translateIcon.setOnClickListener {
            if (!opened) {
                onOpen?.invoke()
                opened = true
            }
        }
    }

    private var opened = false
    private var onOpen: (() -> Unit)? = null
    private var onCollapse: (() -> Unit)? = null

    val root get() = binding.root

    fun loading() {
        binding.translate.isVisible = false
        binding.progressBar.isVisible = true
        binding.unreachableServer.isVisible = false
    }

    fun translate(text: String) {
        binding.translate.text = text
        binding.translate.isVisible = true
        binding.progressBar.isVisible = false
        binding.unreachableServer.isVisible = false
    }

    fun error() {
        binding.translate.isVisible = false
        binding.progressBar.isVisible = false
        binding.unreachableServer.isVisible = true
    }

    fun collapse() {
        Handler().postDelayed({
            binding.margin.isVisible = false
        }, MARGIN_ANIMATION_DELAY)
        binding.translateIcon.requestLayout()
    }

    fun open() {
        Handler().postDelayed({
            binding.margin.isVisible = true
        }, MARGIN_ANIMATION_DELAY)
        binding.translateIcon.requestLayout()
    }

    fun onCollapseClick(block: () -> Unit) {
        onCollapse = block
    }

    fun onOpenClick(block: () -> Unit) {
        onOpen = block
    }

    override fun setWidth(width: Int) {
        binding.root.updateParams(width = width)
    }

    override fun setHeight(height: Int) {
        binding.root.updateParams(height = height)
    }

    override fun requestLayout() {
        binding.root.requestLayout()
    }

    companion object {
        const val MARGIN_ANIMATION_DELAY = 100L
    }
}