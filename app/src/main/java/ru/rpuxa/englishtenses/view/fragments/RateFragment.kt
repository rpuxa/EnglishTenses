package ru.rpuxa.englishtenses.view.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.browse
import ru.rpuxa.englishtenses.databinding.FragmentRateBinding
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.RateViewModel
import androidx.lifecycle.observe
import ru.rpuxa.englishtenses.view.activities.BaseActivity

class RateFragment : Fragment() {

    private val viewModel: RateViewModel by viewModel()
    private val binding by lazy { FragmentRateBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (viewModel.doDismiss()) {
            view.isVisible = false
            return
        }

        binding.dismiss.setOnClickListener {
            viewModel.setDismiss()
            view.isVisible = false
        }

        binding.rate.setOnClickListener {
            browse("https://play.google.com/store/apps/details?id=ru.rpuxa.englishtenses")

            val activity = activity as BaseActivity
            Handler().postDelayed({
                activity.achievementViewModel.onAppRated()
            }, 1000)
            viewModel.setDismiss()
            view.isVisible = false
        }
    }
}