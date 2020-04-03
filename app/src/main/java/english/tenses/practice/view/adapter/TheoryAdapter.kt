package english.tenses.practice.view.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import english.tenses.practice.databinding.ItemExampleBinding
import english.tenses.practice.databinding.ItemTextBinding
import english.tenses.practice.databinding.ItemUsageBinding
import english.tenses.practice.model.enums.Example
import english.tenses.practice.model.enums.Text
import english.tenses.practice.model.enums.Usage

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