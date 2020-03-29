package english.tenses.practice.view.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import english.tenses.practice.R
import english.tenses.practice.model.ExerciseResult
import english.tenses.practice.view.fragments.ExerciseFragment

abstract class ExerciseActivity(private val tipMode: Boolean) : BaseActivity() {

    abstract val binding: ViewBinding
    private var oldFragment: Fragment? = null
    private val tenses by lazy { intent.extras?.get(TENSES) as Set<Int> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    protected fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .apply {
                oldFragment?.let { remove(it) }
            }
            .add(R.id.content, fragment)
            .commit()
        oldFragment = fragment
    }

    protected fun nextExercise() {
        val currentFragment = ExerciseFragment.create(tenses, tipMode)
        currentFragment.setOnNextListener {
            achievementViewModel.onSentenceLearned()
            onResult(it)
        }
        showFragment(currentFragment)
    }

    abstract fun onResult(result: ExerciseResult)


    companion object {
        const val TENSES = "tenses"
    }
}