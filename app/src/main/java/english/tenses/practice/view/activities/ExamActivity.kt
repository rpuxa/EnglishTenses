package english.tenses.practice.view.activities

import android.os.Bundle
import android.util.Log
import english.tenses.practice.databinding.ActivityExamBinding
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.view.fragments.ExamResultFragment
import english.tenses.practice.view.fragments.StartTestFragment
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.ExamViewModel
import androidx.lifecycle.observe
import english.tenses.practice.view.CloseDialog


class ExamActivity : ExerciseActivity(false) {

    override val binding: ActivityExamBinding by lazy { ActivityExamBinding.inflate(layoutInflater) }
    private val viewModel: ExamViewModel by viewModel()
    private var finished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.back.setOnClickListener {
            onBackPressed()
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

    override fun onBackPressed() {
        if (finished) {
            finish()
        } else {
            CloseDialog().show(supportFragmentManager, "")
        }
    }

    companion object {
        val TAG = ExamActivity::class.simpleName + "Debug"
    }
}
