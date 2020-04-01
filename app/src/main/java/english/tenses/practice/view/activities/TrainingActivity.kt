package english.tenses.practice.view.activities

import android.os.Bundle
import english.tenses.practice.databinding.ActivityTrainingBinding
import english.tenses.practice.model.ExerciseResult


class TrainingActivity : ExerciseActivity(true) {

//    private val viewModel: TrainingViewModel by viewModel()

    private var lastClick = 0L

    override val binding: ActivityTrainingBinding by lazy {
        ActivityTrainingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.back.setOnClickListener {
            finish()
        }
        binding.skip.setOnClickListener {
            val current = System.currentTimeMillis()
            if (current - lastClick > 500) {
                lastClick = current
                dismissMenu()
                nextExercise()
            }
        }
        nextExercise()
    }

    override fun onResult(result: ExerciseResult) {
        nextExercise()
    }
}
