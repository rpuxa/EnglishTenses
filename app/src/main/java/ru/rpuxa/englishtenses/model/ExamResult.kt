package ru.rpuxa.englishtenses.model

import ru.rpuxa.englishtenses.model.db.CorrectnessStatistic
import java.io.Serializable

data class ExamResult(
    val time: String,
    val correctness: List<CorrectnessStatistic>,
    val correctnessPercent: String,
    val tensesNumber: Int
) : Serializable