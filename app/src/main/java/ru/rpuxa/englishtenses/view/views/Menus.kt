package ru.rpuxa.englishtenses.view.views

import android.app.Activity
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.databinding.BottomMenuCorrectBinding
import ru.rpuxa.englishtenses.databinding.BottomMenuTipBinding
import ru.rpuxa.englishtenses.databinding.MistakeBottomMenuBinding
import ru.rpuxa.englishtenses.model.Tense

object Menus {

    fun showTipMenu(activity: Activity, tense: Tense, spaceIndex: Int, manyAnswers: Boolean): BottomMenu {
        val binding = BottomMenuTipBinding.inflate(activity.layoutInflater)
        val time = activity.resources.getString(tense.stringRes)
       val text =  if (manyAnswers) {
            activity.resources.getString(
                R.string.many_tip,
                activity.resources.getStringArray(R.array.ordinal_numbers)[spaceIndex],
                time
            )
        } else {
           activity.resources.getString(
               R.string.single_tip,
               time
           )
       }

        binding.text.text = text
        return BottomMenu(binding.root).apply { show(activity) }
    }


    fun showCorrectMenu(activity: Activity, block: () -> Unit): BottomMenu {
        val binding = BottomMenuCorrectBinding.inflate(activity.layoutInflater)
        binding.next.setOnClickListener {
            block()
        }
        return BottomMenu(binding.root).apply { show(activity) }
    }

}