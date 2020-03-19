package ru.rpuxa.englishtenses.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.act
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.FragmentExamResultBinding
import ru.rpuxa.englishtenses.model.ExamResult

class ExamResultFragment : Fragment() {

    private val binding by lazy {
        FragmentExamResultBinding.inflate(layoutInflater)
    }
    private val result by lazy {
        arguments?.get(EXAM_RESULT) as? ExamResult ?: error("Exam result needed")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.correctness.text = result.correctnessPercent
        binding.time.text = getString(R.string.secs, result.time)
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