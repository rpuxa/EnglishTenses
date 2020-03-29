package english.tenses.practice.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import english.tenses.practice.databinding.ItemIrregularVerbBinding
import english.tenses.practice.model.IrregularVerb

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