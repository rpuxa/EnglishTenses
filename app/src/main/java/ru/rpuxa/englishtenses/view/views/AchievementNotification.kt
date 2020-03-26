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
    ActionBar.LayoutParams.WRAP_CONTENT,
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
        showAsDropDown(anchor, margin, margin, Gravity.TOP or Gravity.CENTER_HORIZONTAL)

        contentView.setOnTouchListener {  _, event ->
            dismiss()
            false
        }
        handler.postDelayed(dismissRunnable, DISMISS_TIME)
    }

    override fun dismiss() {
        handler.removeCallbacks(dismissRunnable)
        super.dismiss()
    }

    private val listener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val dist = e1.rawY - e2.rawY
            Log.d("NotificationMyDebug", dist.toString())
            if (dist > 40) {
                dismiss()
            }
            return true
        }
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