package english.tenses.practice.model

import english.tenses.practice.model.db.CorrectnessStatistic

data class ExerciseResult(
    val result: List<CorrectnessStatistic>,
    val allCorrect: Boolean,
    val time: Long
)