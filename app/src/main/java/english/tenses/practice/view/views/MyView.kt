package english.tenses.practice.view.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import english.tenses.practice.R

class MyView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        attrs(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs(context,attrs)
    }

    private fun attrs(context: Context, attrs: AttributeSet) {
        val obtainStyledAttributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.View,
            0,
            0
        )
println()
    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        super.setBackgroundTintList(tint)
        println(tint)
    }
}