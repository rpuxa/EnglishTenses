package english.tenses.practice.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import english.tenses.practice.databinding.FragmentStartTestBinding

class StartTestFragment : Fragment() {
    private val binding by lazy { FragmentStartTestBinding.inflate(layoutInflater) }
    private var listener: (() -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.begin.setOnClickListener {
            listener?.invoke()
        }
    }

    fun setListener(l: () -> Unit) {
        listener = l
    }
}