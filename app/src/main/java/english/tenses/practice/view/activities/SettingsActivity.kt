package english.tenses.practice.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import english.tenses.practice.R
import english.tenses.practice.databinding.ActivitySettingsBinding
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.SettingsViewModel
import androidx.lifecycle.observe
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            viewModel.languages
        )
        binding.spinner.adapter = adapter

        viewModel.nativeLanguage.observe(this) {
            binding.spinner.setSelection(it)
        }

        viewModel.nativeLanguageAutoDetection.observe(this) {
            binding.languageAutoDetection.isChecked = it
            binding.spinner.isEnabled = !it
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.languageSelected(position)
            }
        }

        binding.languageAutoDetection.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoNativeLanguage(isChecked)
        }
    }
}
