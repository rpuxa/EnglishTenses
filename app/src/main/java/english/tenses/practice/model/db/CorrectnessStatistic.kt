package english.tenses.practice.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "correctness_statistic")
data class CorrectnessStatistic(
    @PrimaryKey
    val tenseCode: Int,
    var correct: Int,
    var all: Int
) : Serializable {
    val percent get() = if (all == 0) 0f else correct.toFloat() / all

    fun addResult(correct: Boolean) {
        if (correct) {
            this.correct++
        }
        all++
    }

    operator fun plusAssign(other: CorrectnessStatistic) {
        correct += other.correct
        all += other.all
    }
}