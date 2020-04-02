package english.tenses.practice.view.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class WrappedDummyView : FrameLayout {
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

    val dummyView = DummyView(context)
    init {
        addView(dummyView)
    }
}