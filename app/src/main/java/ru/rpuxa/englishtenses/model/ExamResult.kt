package ru.rpuxa.englishtenses.model

import ru.rpuxa.englishtenses.model.db.CorrectnessStatistic
import java.io.Serializable

data class ExamResult(
    val time: Double,
    val correctness: List<CorrectnessStatistic>,
    val correctnessPercent: Int,
    val tensesNumber: Int
) : Serializable