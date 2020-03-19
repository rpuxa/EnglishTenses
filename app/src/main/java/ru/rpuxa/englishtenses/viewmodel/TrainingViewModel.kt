package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.model.SentenceStatistic
import javax.inject.Inject

class TrainingViewModel @Inject constructor(
    private val sentencesHandler: SentenceStatistic
) : ViewModel() {

    init {
        sentencesHandler.resetSession()
    }

    fun onResult(result: ExerciseResult) {
        sentencesHandler.addResult(result.result)
    }
}