package english.tenses.practice.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.act
import english.tenses.practice.R
import english.tenses.practice.databinding.FragmentExamResultBinding
import english.tenses.practice.model.pojo.ExamResult

class ExamResultFragment : Fragment() {

    private val binding by lazy {
        FragmentExamResultBinding.inflate(layoutInflater)
    }
    private val result by lazy {
        arguments?.get(EXAM_RESULT) as? ExamResult
            ?: error("Exam result needed")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.correctness.text = "${result.correctnessPercent} %"
        binding.time.text = getString(R.string.secs, result.time.toInt())
        binding.totalTenses.text = result.tensesNumber.toString()
        binding.exit.setOnClickListener {
            act.finish()
        }
    }

    companion object {
        private const val EXAM_RESULT = "res"
        fun create(examResult: ExamResult) =
            ExamResultFragment().apply {
                arguments = bundleOf(
                    EXAM_RESULT to examResult
                )
            }

    }
}