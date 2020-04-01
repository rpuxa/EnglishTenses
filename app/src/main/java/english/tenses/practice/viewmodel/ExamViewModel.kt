package english.tenses.practice.viewmodel

import androidx.lifecycle.ViewModel
import english.tenses.practice.State
import english.tenses.practice.model.ExamResult
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.model.SentenceStatistic
import english.tenses.practice.model.db.CorrectnessStatistic
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.roundToInt

class ExamViewModel @Inject constructor(
) : ViewModel() {
    private var exerciseNumber = 0
    private var consumedTime = 0L
    private val correctness = HashMap<Int, CorrectnessStatistic>()
    private val _progress = State(0f)

    val progress = _progress.liveData

    fun onResult(result: ExerciseResult): ExamResult? {
        consumedTime += result.time
        exerciseNumber++
        result.result.forEach {
            val s = correctness[it.tenseCode] ?: CorrectnessStatistic(it.tenseCode, 0, 0)
            s += it
            correctness[it.tenseCode] = s
        }
        _progress.value = exerciseNumber.toFloat() / TEST_EXERCISE_AMOUNT
        return if (exerciseNumber == TEST_EXERCISE_AMOUNT) {
            val totalTenses = correctness.values.sumBy { it.all }
            val totalCorrect = correctness.values.sumBy { it.correct }
            ExamResult(
                consumedTime.toDouble() / 1000,
                correctness.values.toList(),
                (totalCorrect.toDouble() * 100 / totalTenses).roundToInt(),
                totalTenses
            )
        } else {
            null
        }
    }

    companion object {
        private const val TEST_EXERCISE_AMOUNT = 10
        private val FORMAT = DecimalFormat(".##")
    }
}