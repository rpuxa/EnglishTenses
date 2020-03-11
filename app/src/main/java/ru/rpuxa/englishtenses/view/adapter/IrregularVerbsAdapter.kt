package ru.rpuxa.englishtenses.view.adapter

import android.view.LayoutInflater
import android.view.View
import ru.rpuxa.englishtenses.databinding.ItemIrregularVerbBinding
import ru.rpuxa.englishtenses.model.IrregularVerb

class IrregularVerbsAdapter : BaseAdapter<IrregularVerb, ItemIrregularVerbBinding>() {

    override val LayoutInflater.binding get() = ItemIrregularVerbBinding.inflate(this)

    override fun View.getViewHolder(binding: ItemIrregularVerbBinding) =
        bind {
            binding.first.text = it.first
            binding.second.text = it.second
            binding.third.text = it.third
        }
}