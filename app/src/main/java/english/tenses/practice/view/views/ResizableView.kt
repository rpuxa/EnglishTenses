package english.tenses.practice.view.views

import android.view.View

interface ResizableView {
    fun setWidth(width: Int)
    fun setHeight(height: Int)
    fun requestLayout()
}