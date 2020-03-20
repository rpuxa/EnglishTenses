package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rpuxa.englishtenses.databinding.ActivityTheoryBinding
import ru.rpuxa.englishtenses.model.Tense
import ru.rpuxa.englishtenses.model.Theory
import ru.rpuxa.englishtenses.view.adapter.TheoryAdapter

class TheoryActivity : BaseActivity() {

    private val binding by lazy { ActivityTheoryBinding.inflate(layoutInflater) }
    private val tenseCode by lazy { intent.extras?.get(TENSE_CODE) as? Int ?: error("Code needed") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = TheoryAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        adapter.submitList(Theory.byTense(tenseCode).usages)

        binding.back.setOnClickListener {
            finish()
        }

        binding.tense.setText(Tense[tenseCode].stringRes)
    }

    companion object {
        const val TENSE_CODE = "tc"
    }
}
