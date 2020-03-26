package ru.rpuxa.englishtenses.model

import ru.rpuxa.englishtenses.model.db.CorrectnessStatistic

data class  ExerciseResult(
    val result: List<CorrectnessStatistic>,
    val allCorrect: Boolean,
    val time: Long
)