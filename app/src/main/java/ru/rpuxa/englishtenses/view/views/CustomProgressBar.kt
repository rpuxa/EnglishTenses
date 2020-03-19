package ru.rpuxa.englishtenses.view.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import ru.rpuxa.englishtenses.R
import kotlin.math.sqrt
import kotlin.properties.Delegates

class CustomProgressBar : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var progress by Delegates.observable(0f) { _, old, new ->
        if (old != new) {
            invalidate()
        }
    }

    private val backgroundColor = ContextCompat.getColor(context, R.color.progressbar_background)
    private val progressColor = ContextCompat.getColor(context, R.color.progressbar_progress)
    private val flareColor = ContextCompat.getColor(context, R.color.progressbar_flare)

    private val paint = Paint()
    override fun onDraw(canvas: Canvas) {
        val radius = height / 2f
        paint.color = backgroundColor
        canvas.drawCircle(radius, radius, radius, paint)
        canvas.drawCircle(width - radius, radius, radius, paint)
        canvas.drawRect(radius, 0f, width - radius, height.toFloat(), paint)


        paint.color = progressColor
        val endPoint = radius + (width - 2 * radius) * progress
        canvas.drawCircle(radius, radius, radius, paint)
        canvas.drawCircle(endPoint, radius, radius, paint)
        canvas.drawRect(radius, 0f, endPoint, height.toFloat(), paint)

        paint.color = flareColor
        val flareStart = width * .03f + radius
        val flareEnd = endPoint - width * .03f

        if (flareStart < flareEnd) {
            val flareY = height / 3f
            val flareRadius = height / 7f

            canvas.drawCircle(flareStart, flareY, flareRadius, paint)
            canvas.drawCircle(flareEnd, flareY, flareRadius, paint)
            canvas.drawRect(
                flareStart,
                flareY - flareRadius,
                flareEnd,
                flareY + flareRadius,
                paint
            )
        }
    }

    private var animator: Animator? = null
    fun animateProgress(needed: Float) {
        animator?.cancel()
        animator = ObjectAnimator.ofFloat(progress, needed)
            .apply {
                duration = 1000L
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    progress = it.animatedValue as Float
                }
                start()
            }
    }
}