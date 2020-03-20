package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.ActivityAchievementsBinding
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rpuxa.englishtenses.view.adapter.AchievementAdapter

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
            binding.allStarsAmout.text = it.sumBy { it.starsCount() }.toString()
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        binding.back.setOnClickListener {
            finish()
        }
    }
}
