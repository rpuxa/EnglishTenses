package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rpuxa.englishtenses.databinding.ActivityIrregularVerbsBinding
import ru.rpuxa.englishtenses.view.adapter.IrregularVerbsAdapter
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.IrregularVerbsViewModel

class IrregularVerbsActivity : AppCompatActivity() {

    private val viewModel: IrregularVerbsViewModel by viewModel()
    private val binding by lazy { ActivityIrregularVerbsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = IrregularVerbsAdapter()
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        adapter.submitList(viewModel.verbs)

        binding.close.setOnClickListener {
            finish()
        }
    }
}
