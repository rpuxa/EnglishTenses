package english.tenses.practice.view.activities

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import english.tenses.practice.BuildConfig
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import english.tenses.practice.R
import english.tenses.practice.databinding.ActivityMainBinding
import english.tenses.practice.databinding.TenseBottomMenuBinding
import english.tenses.practice.model.RemoteSentenceLoader
import english.tenses.practice.model.Tense
import english.tenses.practice.view.views.BottomMenu
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.MainViewModel
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity() {

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

            tenseBinding.root.setOnLongClickListener {
                viewModel.changeState(index)
                true
            }
        }

        viewModel.chosen.observe(this) { set ->
            bindings.forEachIndexed { index, tenseBinding ->
                tenseBinding.checkBox.isChecked = index in set
                tenseBinding.checkBox.isVisible = set.isNotEmpty()
            }
            binding.choseAllCheckbox.isChecked = set.size == Tense.values().size

            val visible = set.isNotEmpty()
            binding.test.isVisible = visible
            binding.training.isVisible = visible
            if (set.isNotEmpty()) {
                dismissButtonMenu()
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
                    viewModel.changeState(viewModel.showTenseDialog.value!!.code)
                }
                menuBinding.theory.setOnClickListener {
                    startActivity<TheoryActivity>(
                        TheoryActivity.TENSE_CODE to viewModel.showTenseDialog.value!!.code
                    )
                    dismissButtonMenu()
                }
                showBottomMenu(TenseBottomMenu(menuBinding))
            }
        }

        viewModel.correctness.observe(this) {
            it.forEach {
                bindings[it.tenseCode].progressBar.animateProgress(it.percent)
            }
        }

        binding.choseAll.setOnClickListener {
            viewModel.changeAllState()
        }


        binding.test.setOnClickListener {
            if (canStartExercise()) {
                startActivity<ExamActivity>(
                    ExerciseActivity.TENSES to viewModel.chosen.value
                )
            }
        }
        binding.training.setOnClickListener {
            if (canStartExercise()) {
                startActivity<TrainingActivity>(
                    ExerciseActivity.TENSES to viewModel.chosen.value
                )
            }
        }

        binding.irregularVerbs.setOnClickListener {
            startActivity<IrregularVerbsActivity>()
        }

        binding.achievements.setOnClickListener {
            startActivity<AchievementsActivity>()
        }
    }


    private var bottomMenu: BottomMenu? = null
    private fun showBottomMenu(menu: BottomMenu) {
        bottomMenu?.dismiss()
        menu.show(this)
        bottomMenu = menu
    }

    private fun dismissButtonMenu() {
        bottomMenu?.dismiss()
        bottomMenu = null
    }

    private fun canStartExercise(): Boolean {
        if (viewModel.chosen.value!!.size < 2) {
            toast(R.string.cant_start_exercise)
            return false
        }
        return true
    }

    private class TenseBottomMenu(val binding: TenseBottomMenuBinding) : BottomMenu(binding.root)

    companion object {
        const val TAG = "MainActivityDebug"
    }
}
