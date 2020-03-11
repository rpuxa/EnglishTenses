package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import ru.rpuxa.englishtenses.databinding.ActivityExamBinding
import ru.rpuxa.englishtenses.model.ExerciseResult

class ExamActivity : ExerciseActivity(false) {

    override val binding: ActivityExamBinding by lazy { ActivityExamBinding.inflate(layoutInflater) }
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
