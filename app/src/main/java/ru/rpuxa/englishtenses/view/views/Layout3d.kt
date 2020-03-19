package ru.rpuxa.englishtenses.view.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.viewRect
import kotlin.properties.Delegates


open class Layout3d : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected var backTint = android.R.color.white
    private var hasPressed by Delegates.observable(false) { _, old, new ->
        if (old != new) {
            requestLayout()
        }
    }
    private val margin = resources.getDimensionPixelSize(R.dimen.button3d_margin)
    private val buttonDepth = resources.getDimensionPixelSize(R.dimen.button3d_depth)


    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)


        val child: View = getChildAt(0)
        child.measure(
            MeasureSpec.makeMeasureSpec(width - margin * 2, widthMode),
            MeasureSpec.makeMeasureSpec(height - margin * 2, heightMode)
        )
        setMeasuredDimension(
            child.measuredWidth + margin * 2,
            child.measuredHeight + margin * 2 + buttonDepth
        )
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(
            context,
            if (hasPressed) R.drawable.button3d_pressed else R.drawable.button3d_unpressed
        )!!
        drawable.setTintMode(PorterDuff.Mode.MULTIPLY)
        drawable.setTint(ContextCompat.getColor(context, backTint))
        drawable.setBounds(0, if (hasPressed) buttonDepth else 0, width, height)
        drawable.draw(canvas)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val x = x.toInt()
        val y = y.toInt()
        getChildAt(0).layout(
            l - x + margin,
            t - y + margin + if (hasPressed) buttonDepth else 0,
            r - x - margin,
            b - y - margin - if (hasPressed) 0 else buttonDepth
        )
    }

    final override fun setWillNotDraw(willNotDraw: Boolean) {
        super.setWillNotDraw(willNotDraw)
    }

    private var longPressed = false
    private val longPress = Runnable {
        if (performLongClick()) {
            longPressed = true
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
       return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handler.postDelayed(longPress, LONG_PRESS_TIME)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                hasPressed = viewRect.contains(event.rawX.toInt(), event.rawY.toInt())
                if (!hasPressed) {
                    handler.removeCallbacks(longPress)
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacks(longPress)
                if (!longPressed && hasPressed) {
                    performClick()
                }
                hasPressed = false
                longPressed = false
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                handler.removeCallbacks(longPress)
                hasPressed = false
                longPressed = false
                true
            }
            else -> false
        }
    }

    companion object {
        const val LONG_PRESS_TIME = 500L
    }
}