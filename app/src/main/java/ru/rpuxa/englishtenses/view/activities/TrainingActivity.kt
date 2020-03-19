package ru.rpuxa.englishtenses.view.activities

import android.os.Bundle
import ru.rpuxa.englishtenses.databinding.ActivityTrainingBinding
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.TrainingViewModel

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
    }
}
