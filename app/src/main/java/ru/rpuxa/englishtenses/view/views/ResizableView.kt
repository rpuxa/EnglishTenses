package ru.rpuxa.englishtenses.view.views

interface ResizableView {
    fun setWidth(width: Int)
    fun setHeight(height: Int)
    fun requestLayout()
}