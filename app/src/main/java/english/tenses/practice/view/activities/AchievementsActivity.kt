package english.tenses.practice.view.activities

import android.os.Bundle
import english.tenses.practice.databinding.ActivityAchievementsBinding
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import english.tenses.practice.view.adapter.AchievementAdapter

class AchievementsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAchievementsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = AchievementAdapter()
        achievementViewModel.all.observe(this) {
            adapter.submitList(it)
            binding.allStarsAmount.text = it.sumBy { it.starsCount() }.toString()
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        binding.back.setOnClickListener {
            finish()
        }
    }
}
