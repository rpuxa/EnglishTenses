package english.tenses.practice.view.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import english.tenses.practice.R
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.view.fragments.ExerciseFragment

abstract class ExerciseActivity(private val tipMode: Boolean) : BaseActivity() {

    abstract val binding: ViewBinding
    private val tenses by lazy { intent.extras?.get(TENSES) as Set<Int> }
    protected var currentFragment: ExerciseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    protected fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.content, fragment)
            .commit()
    }

    protected fun nextExercise() {
        val fragment = ExerciseFragment.create(tenses, tipMode)
        fragment.setOnNextListener {
            achievementViewModel.onSentenceLearned()
            onResult(it)
        }
        showFragment(fragment)
        currentFragment = fragment
    }

    protected fun dismissMenu() {
        currentFragment?.dismissMenu()
    }

    abstract fun onResult(result: ExerciseResult)


    companion object {
        const val TENSES = "tenses"
    }
}