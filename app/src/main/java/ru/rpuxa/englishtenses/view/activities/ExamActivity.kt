package ru.rpuxa.englishtenses.view.activities

import android.os.Bundle
import android.util.Log
import ru.rpuxa.englishtenses.databinding.ActivityExamBinding
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.view.fragments.ExamResultFragment
import ru.rpuxa.englishtenses.view.fragments.StartTestFragment
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.ExamViewModel
import androidx.lifecycle.observe
import ru.rpuxa.englishtenses.view.CloseDialog


class ExamActivity : ExerciseActivity(false) {

    override val binding: ActivityExamBinding by lazy { ActivityExamBinding.inflate(layoutInflater) }
    private val viewModel: ExamViewModel by viewModel()
    private var finished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.back.setOnClickListener {
            if (finished) {
                finish()
            } else {
                CloseDialog().show(supportFragmentManager, "")
            }
        }
        val startFragment = StartTestFragment()

        startFragment.setListener {
            nextExercise()
        }

        showFragment(startFragment)
        viewModel.progress.observe(this) {
            binding.customProgressBar.animateProgress(it)
        }
    }

    override fun onResult(result: ExerciseResult) {
        Log.d(TAG, result.toString())
        val examResult = viewModel.onResult(result)
        if (examResult == null) {
            nextExercise()
        } else {
            achievementViewModel.onTestPassed()
            if (examResult.correctnessPercent >= 70) {
                achievementViewModel.onTest70PercentPassed()
            }
            if (examResult.correctnessPercent == 100) {
                achievementViewModel.onCorrectTestPassed()
            }
            finished = true
            showFragment(ExamResultFragment.create(examResult))
        }
    }

    companion object {
        val TAG = ExamActivity::class.simpleName + "Debug"
    }
}
