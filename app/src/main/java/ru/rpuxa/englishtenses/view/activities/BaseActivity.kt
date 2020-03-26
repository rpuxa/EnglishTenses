package ru.rpuxa.englishtenses.view.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.AchievementViewModel
import androidx.lifecycle.observe
import ru.rpuxa.englishtenses.view.views.AchievementNotification

abstract class BaseActivity : AppCompatActivity() {

    val achievementViewModel: AchievementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        achievementViewModel.newAchievementEvent.observe(this) {
            AchievementNotification.show(this, it)
        }
    }
}