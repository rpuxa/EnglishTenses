package ru.rpuxa.englishtenses.view.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import ru.rpuxa.englishtenses.databinding.ItemExampleBinding
import ru.rpuxa.englishtenses.databinding.ItemTextBinding
import ru.rpuxa.englishtenses.databinding.ItemUsageBinding
import ru.rpuxa.englishtenses.model.Example
import ru.rpuxa.englishtenses.model.Text
import ru.rpuxa.englishtenses.model.Usage

class TheoryAdapter : BaseMultiItemAdapter2() {

    @SuppressLint("SetTextI18n")
    override fun Builder.build() {
        item<Usage, ItemUsageBinding> {
            binding = { layoutInflater, viewGroup ->
                ItemUsageBinding.inflate(
                    layoutInflater,
                    viewGroup,
                    false
                )
            }

            bind = { item, binding ->
                (binding.root as TextView).text = item.number.toString() + ") ${getString(item.name)}"
            }
        }

        item<Text, ItemTextBinding> {
            binding = { layoutInflater, viewGroup ->
                ItemTextBinding.inflate(
                    layoutInflater,
                    viewGroup,
                    false
                )
            }

            bind = { item, binding ->
                (binding.root as TextView).setText(item.text)
            }
        }

        item<Example, ItemExampleBinding> {
            binding = { layoutInflater, viewGroup ->
                ItemExampleBinding.inflate(
                    layoutInflater,
                    viewGroup,
                    false
                )
            }

            bind = { item, binding ->
                (binding.root as TextView).text = item.text
            }
        }
    }
}