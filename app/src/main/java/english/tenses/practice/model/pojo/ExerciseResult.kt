package english.tenses.practice.model.pojo

import english.tenses.practice.model.db.entity.CorrectnessStatistic

data class ExerciseResult(
    val result: List<CorrectnessStatistic>,
    val allCorrect: Boolean,
    val time: Long
)