package ru.rpuxa.englishtenses.view.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import org.jetbrains.anko.toast
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.ActivityTrainingBinding
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.view.fragments.ExerciseFragment

class TrainingActivity : ExerciseActivity(true) {

    override val binding: ActivityTrainingBinding by lazy {
        ActivityTrainingBinding.inflate(
            layoutInflater
        )
    }
    override val content: FragmentContainerView get() = binding.content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun onResult(result: ExerciseResult) {

    }
}
