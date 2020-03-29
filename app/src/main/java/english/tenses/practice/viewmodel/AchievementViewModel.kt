package english.tenses.practice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import english.tenses.practice.SingleLiveEvent
import english.tenses.practice.model.*
import english.tenses.practice.model.db.AchievementDao
import javax.inject.Inject

class AchievementViewModel @Inject constructor(
    private val achievementDao: AchievementDao
) : ViewModel() {
    val newAchievementEvent = SingleLiveEvent<String>()
    val all by lazy { achievementDao.all }

    private fun update(id: Int, block: (Int) -> Int) {
        viewModelScope.launch {
            val entity = achievementDao.get(id)
            val achievement = Achievement.create(id, entity.progress)
            entity.progress = block(entity.progress)
            achievement.setProgress(entity.progress)?.let(newAchievementEvent::setValue)
            achievementDao.update(entity)
        }
    }

    private fun updateAdd(id: Int) {
        update(id) {
            it + 1
        }
    }

    private fun updateBoolean(id: Int) {
        update(id) {
            1
        }
    }

    fun onTheoryOpened() {
        updateBoolean(OpenTheoryAchievement.ID)
    }

    fun onAppRated() {
        updateBoolean(RateAppAchievement.ID)
    }

    fun onSentenceLearned() {
        updateAdd(LearnedSentencesAchievement.ID)
    }

    fun onTest70PercentPassed() {
        updateAdd(ComboTestAchievement.ID)
    }

    fun onCorrectTestPassed() {
        updateAdd(EntireCorrectTestAchievement.ID)
    }

    fun onTestPassed() {
        updateAdd(PassTestAchievement.ID)
    }

}