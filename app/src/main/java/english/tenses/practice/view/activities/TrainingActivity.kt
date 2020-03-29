package english.tenses.practice.view.activities

import android.os.Bundle
import english.tenses.practice.databinding.ActivityTrainingBinding
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.TrainingViewModel

class TrainingActivity : ExerciseActivity(true) {

    private val viewModel: TrainingViewModel by viewModel()

    override val binding: ActivityTrainingBinding by lazy {
        ActivityTrainingBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.back.setOnClickListener {
            finish()
        }
        nextExercise()
    }

    override fun onResult(result: ExerciseResult) {
        viewModel.onResult(result)
        nextExercise()
    }
}
