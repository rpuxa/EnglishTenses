package ru.rpuxa.englishtenses.view.views

import android.app.ActionBar
import android.app.Activity
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import org.jetbrains.anko.contentView
import org.jetbrains.anko.sdk27.coroutines.onTouch
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.AchievementNotificationBinding
import ru.rpuxa.englishtenses.dpToPx
import ru.rpuxa.englishtenses.onMeasured

class AchievementNotification(
    private val text: String,
    private val binding: AchievementNotificationBinding
) : PopupWindow(
    binding.root,
    ActionBar.LayoutParams.MATCH_PARENT,
    ActionBar.LayoutParams.WRAP_CONTENT
) {

    init {
        animationStyle = R.style.AchievementAnimationStyle
    }

    private val handler = Handler()
    private val margin = 20.dpToPx()

    fun show(anchor: View) {
        binding.text.text = text
        val contentView = contentView!!
        val width = contentView.width - margin * 4
        showAsDropDown(anchor, margin, margin)
        update(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        contentView.setOnTouchListener { _, _ ->
            dismiss()
            false
        }
        handler.postDelayed(dismissRunnable, DISMISS_TIME)
    }

    override fun dismiss() {
        handler.removeCallbacks(dismissRunnable)
        super.dismiss()
    }

    private val dismissRunnable = Runnable {
        dismiss()
    }


    companion object {
        fun show(activity: Activity, text: String) {
            AchievementNotification(
                text,
                AchievementNotificationBinding.inflate(
                    activity.layoutInflater
                )
            ).show(activity.contentView!!)
        }

        const val DISMISS_TIME = 5000L
    }
}