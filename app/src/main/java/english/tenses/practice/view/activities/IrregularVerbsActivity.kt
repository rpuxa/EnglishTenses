package english.tenses.practice.view.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import english.tenses.practice.databinding.ActivityIrregularVerbsBinding
import english.tenses.practice.view.adapter.IrregularVerbsAdapter
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.IrregularVerbsViewModel
import androidx.lifecycle.observe
import english.tenses.practice.textChangeFlow

class IrregularVerbsActivity : BaseActivity() {

    private val viewModel: IrregularVerbsViewModel by viewModel()
    private val binding by lazy { ActivityIrregularVerbsBinding.inflate(layoutInflater) }
    private val searchQuery by lazy { intent.extras?.getString(SEARCH_QUERY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = IrregularVerbsAdapter()
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        viewModel.verbs.observe(this) {
            adapter.submitList(it)
        }

        binding.close.setOnClickListener {
            finish()
        }

        binding.search.setText(searchQuery)
        viewModel.searchFlow(binding.search.textChangeFlow())
    }

    companion object {
        const val SEARCH_QUERY = "sq"
    }
}
