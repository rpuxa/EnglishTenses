package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rpuxa.englishtenses.databinding.ActivityIrregularVerbsBinding
import ru.rpuxa.englishtenses.textChangeFlow
import ru.rpuxa.englishtenses.view.adapter.IrregularVerbsAdapter
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.IrregularVerbsViewModel
import androidx.lifecycle.observe

class IrregularVerbsActivity : AppCompatActivity() {

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
