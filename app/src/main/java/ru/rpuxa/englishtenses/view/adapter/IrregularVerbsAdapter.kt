package ru.rpuxa.englishtenses.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rpuxa.englishtenses.databinding.ItemIrregularVerbBinding
import ru.rpuxa.englishtenses.model.IrregularVerb

class IrregularVerbsAdapter : BaseAdapter<IrregularVerb, ItemIrregularVerbBinding>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ItemIrregularVerbBinding =
        ItemIrregularVerbBinding.inflate(inflater, parent, false)

    override fun View.getViewHolder(binding: ItemIrregularVerbBinding) =
        bind {
            binding.first.text = it.first
            binding.second.text = it.secondText
            binding.third.text = it.thirdText
        }
}