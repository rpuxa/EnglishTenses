package ru.rpuxa.englishtenses.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import org.jetbrains.anko.startActivity
import ru.rpuxa.englishtenses.databinding.ActivityMainBinding
import ru.rpuxa.englishtenses.databinding.ChoseBottomMenuBinding
import ru.rpuxa.englishtenses.databinding.TenseBottomMenuBinding
import ru.rpuxa.englishtenses.model.Tense
import ru.rpuxa.englishtenses.view.views.BottomMenu
import ru.rpuxa.englishtenses.viewModel
import ru.rpuxa.englishtenses.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bindings = arrayOf(
            binding.t1,
            binding.t2,
            binding.t3,
            binding.t4,
            binding.t5,
            binding.t6,
            binding.t7,
            binding.t8,
            binding.t9,
            binding.t10,
            binding.t11,
            binding.t12
        )

        bindings.forEachIndexed { index, tenseBinding ->
            val name = getString(Tense[index].stringRes)
            tenseBinding.name.text = name.replace(' ', '\n')

            tenseBinding.root.setOnClickListener {
                viewModel.tenseClick(index)
            }
        }

        viewModel.chosen.observe(this) { set ->
            bindings.forEachIndexed { index, tenseBinding ->
                tenseBinding.checkBox.isChecked = index in set
                tenseBinding.checkBox.isVisible = set.isNotEmpty()
            }
            binding.choseAllCheckbox.isChecked = set.size == Tense.values().size

            if (set.isEmpty()) {
                bottomMenu?.dismiss()
            } else if (bottomMenu.let { it == null || it.dismissed || it !is ChoseBottomMenu }) {
                val choseBinding = ChoseBottomMenuBinding.inflate(layoutInflater)
                choseBinding.cancel.setOnClickListener {
                    viewModel.clearAll()
                }
                choseBinding.test.setOnClickListener {
                    startActivity<ExamActivity>(
                        ExerciseActivity.TENSES to viewModel.chosen.value
                    )
                }
                choseBinding.training.setOnClickListener {
                    startActivity<TrainingActivity>(
                        ExerciseActivity.TENSES to viewModel.chosen.value
                    )
                }
                showBottomMenu(ChoseBottomMenu(choseBinding.root))
            }
        }

        viewModel.showTenseDialog.observe(this) { tense ->
            val name = getString(tense.stringRes)
            val bottomMenu = bottomMenu
            if (bottomMenu is TenseBottomMenu && !bottomMenu.dismissed) {
                bottomMenu.binding.tenseName.text = name
            } else {
                val menuBinding = TenseBottomMenuBinding.inflate(layoutInflater)
                menuBinding.tenseName.text = name
                menuBinding.chose.setOnClickListener {
                    viewModel.changeState(tense.code)
                }
                menuBinding.theory.setOnClickListener {

                }
                showBottomMenu(TenseBottomMenu(menuBinding))
            }
        }

        binding.choseAll.setOnClickListener {
            viewModel.changeAllState()
        }

        binding.irregularVerbs.setOnClickListener {
            startActivity<IrregularVerbsActivity>()
        }
    }


    private var bottomMenu: BottomMenu? = null
    fun showBottomMenu(menu: BottomMenu) {
        bottomMenu?.dismiss()
        menu.show(this)
        bottomMenu = menu
    }

    private class ChoseBottomMenu(view: View) : BottomMenu(view)
    private class TenseBottomMenu(val binding: TenseBottomMenuBinding) : BottomMenu(binding.root)
}
