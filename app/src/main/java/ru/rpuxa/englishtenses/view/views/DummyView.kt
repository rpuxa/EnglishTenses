package ru.rpuxa.englishtenses.view.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View

class DummyView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRef: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRef
    )

    private var animator: Animator? = null
    private var mWidth = 0
    private var mHeight = 0

    fun setWidth(width: Int) {
        mWidth = width
    }

    fun setHeight(height: Int) {
        mHeight = height
    }

    fun animateWidth(width: Int, duration: Long) {
        animator?.cancel()
        val animator =
            ObjectAnimator.ofInt(mWidth, width)
                .setDuration(duration)

        animator.addUpdateListener {
            val w = it.animatedValue as Int
            mWidth = w
            requestLayout()
        }
        animator.start()
        this.animator = animator
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mWidth, mHeight)
    }
}