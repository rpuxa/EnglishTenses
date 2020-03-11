package ru.rpuxa.englishtenses.view.views

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.dpToPx
import ru.rpuxa.englishtenses.viewRect

open class Layout3d : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var hasPressed = false
    private val buttonDepth = resources.getDimensionPixelSize(R.dimen.button3d_depth)
    protected val singleChild: View?
        get() {
            if (childCount < 2) return null
            require(childCount == 2) { "Layout3d can contains only 1 child" }
            return getChildAt(1)
        }
    private val marginView = DummyView(context)

    init {
        orientation = VERTICAL
        addView(marginView)
        unpressed()
    }

    protected fun unpressed() {
        Log.d(TAG, "Unpressed")
        marginView.height = 0
        singleChild?.backgroundTintMode = PorterDuff.Mode.MULTIPLY
        singleChild?.setBackgroundResource(R.drawable.button3d_unpressed)
        hasPressed = false
        marginView.requestLayout()
        requestLayout()
    }

    private fun pressed() {
        Log.d(TAG, "Pressed")
        marginView.height = buttonDepth
        singleChild?.backgroundTintMode = PorterDuff.Mode.MULTIPLY
        singleChild?.setBackgroundResource(R.drawable.button3d_pressed)
        hasPressed = true
        marginView.requestLayout()
        requestLayout()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        unpressed()
    }

    private var entered = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_MOVE -> {
                entered = if (viewRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    pressed()
                    true
                } else {
                    unpressed()
                    false
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                if (entered) {
                    unpressed()
                    entered = false
                    performClick()
                }
                true
            }
            else -> false
        }
    }

    companion object {
        private val TAG = Layout3d::class.simpleName
    }
}