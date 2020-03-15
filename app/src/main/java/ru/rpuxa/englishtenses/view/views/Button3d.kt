package ru.rpuxa.englishtenses.view.views

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import org.jetbrains.anko.textColor
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.dpToPx
import ru.rpuxa.englishtenses.viewRect

class Button3d : Layout3d {

    private val text = LayoutInflater.from(context).inflate(R.layout.button3d_text, this, false) as TextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        attrs(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs(context, attrs)
    }



    private fun attrs(context: Context, attrs: AttributeSet) {
        val obtainStyledAttributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Button3d,
            0,
            0
        )

        val txt = obtainStyledAttributes.getString(R.styleable.Button3d_button3dText)
        text.text = txt

        val (color, textColor) = when (obtainStyledAttributes.getInteger(R.styleable.Button3d_button3dStyle, 0)) {
            0 -> android.R.color.white to R.color.colorGrayButton
            1 -> R.color.colorGreenButton to android.R.color.white
            2 -> R.color.colorYellowButton to android.R.color.white
            3 -> R.color.colorBlueButton to android.R.color.white
            4 ->  android.R.color.white to R.color.colorBlueButton
            5 -> R.color.colorRedButton to android.R.color.white

            else -> error("Unknown attribute")
        }
        backTint = color
        text.textColor = ContextCompat.getColor(context, textColor)

        addView(text)
    }

}
