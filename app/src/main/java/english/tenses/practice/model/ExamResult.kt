package english.tenses.practice.model

import english.tenses.practice.model.db.CorrectnessStatistic
import java.io.Serializable

data class ExamResult(
    val time: Double,
    val correctness: List<CorrectnessStatistic>,
    val correctnessPercent: Int,
    val tensesNumber: Int
) : Serializable