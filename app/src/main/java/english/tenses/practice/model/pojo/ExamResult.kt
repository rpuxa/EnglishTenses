package english.tenses.practice.model.pojo

import english.tenses.practice.model.db.entity.CorrectnessStatistic
import java.io.Serializable

data class ExamResult(
    val time: Double,
    val correctness: List<CorrectnessStatistic>,
    val correctnessPercent: Int,
    val tensesNumber: Int
) : Serializable