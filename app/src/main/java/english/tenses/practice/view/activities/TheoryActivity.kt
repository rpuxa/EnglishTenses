package english.tenses.practice.view.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import english.tenses.practice.databinding.ActivityTheoryBinding
import english.tenses.practice.model.enums.Tense
import english.tenses.practice.model.enums.Theory
import english.tenses.practice.onMeasured
import english.tenses.practice.view.adapter.TheoryAdapter

class TheoryActivity : BaseActivity() {

    private val binding by lazy { ActivityTheoryBinding.inflate(layoutInflater) }
    private val tenseCode by lazy { intent.extras?.get(TENSE_CODE) as? Int ?: error("Code needed") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.root.onMeasured {
            achievementViewModel.onTheoryOpened()
        }
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
