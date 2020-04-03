package english.tenses.practice.view.views

import android.app.Activity
import english.tenses.practice.R
import english.tenses.practice.databinding.BottomMenuTipBinding
import english.tenses.practice.model.enums.Tense

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


}