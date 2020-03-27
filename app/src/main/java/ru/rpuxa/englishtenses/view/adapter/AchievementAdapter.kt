package ru.rpuxa.englishtenses.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import ru.rpuxa.englishtenses.databinding.ItemAchievementBinding
import ru.rpuxa.englishtenses.model.Achievement

class AchievementAdapter : BaseAdapter<Achievement, ItemAchievementBinding>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ItemAchievementBinding {
        return ItemAchievementBinding.inflate(inflater, parent, false)
    }

    override fun View.getViewHolder(binding: ItemAchievementBinding) =
        bind {
            binding.ratingBar.max = it.steps.size
            binding.ratingBar.numStars = it.steps.size
            binding.ratingBar.rating = it.starsCount().toFloat()


            binding.title.text = it.title()
            val subtitle = it.subtitle()
            binding.subtitle.isVisible = subtitle.isNotEmpty()
            binding.subtitle.text = subtitle
        }
}