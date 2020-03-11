package ru.rpuxa.englishtenses.view.views

import android.app.Activity
import android.os.Build
import android.transition.AutoTransition
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.contentView
import ru.rpuxa.englishtenses.R

open class BottomMenu(view: View) {
    var dismissed = false
        private set
    private val window = PopupWindow(
        view,
        ViewGroup.LayoutParams.MATCH_PARENT,
        view.resources.getDimensionPixelSize(R.dimen.bottom_menu_height)
    ).apply {
        animationStyle = R.style.BottomMenuAnimation
    }

    fun show(activity: Activity) {
        val anchor = activity.contentView!!
        window.showAsDropDown(
            anchor,
            0,
            anchor.height
        )
    }

    fun dismiss() {
        window.dismiss()
        dismissed = true
    }
}