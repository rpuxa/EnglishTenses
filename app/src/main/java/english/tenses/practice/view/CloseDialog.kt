package english.tenses.practice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.support.v4.act
import english.tenses.practice.R
import english.tenses.practice.databinding.DialogCloseBinding

class CloseDialog : DialogFragment() {
    private val binding by lazy { DialogCloseBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.yes.setOnClickListener {
            dismiss()
            act.finish()
        }
        binding.no.setOnClickListener {
            dismiss()
        }
    }
}