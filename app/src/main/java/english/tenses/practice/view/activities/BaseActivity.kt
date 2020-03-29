package english.tenses.practice.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.AchievementViewModel
import androidx.lifecycle.observe
import english.tenses.practice.view.views.AchievementNotification

abstract class BaseActivity : AppCompatActivity() {

    val achievementViewModel: AchievementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        achievementViewModel.newAchievementEvent.observe(this) {
            AchievementNotification.show(this, it)
        }
    }
}