package english.tenses.practice.viewmodel

import androidx.lifecycle.ViewModel
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.model.SentenceStatistic
import javax.inject.Inject

class TrainingViewModel @Inject constructor(
    private val sentencesHandler: SentenceStatistic
) : ViewModel() {

    fun onResult(result: ExerciseResult) {
        sentencesHandler.addResult(result.result)
    }
}