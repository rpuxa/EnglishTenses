package ru.rpuxa.englishtenses

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View

class DummyView(context: Context) : View(context) {

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