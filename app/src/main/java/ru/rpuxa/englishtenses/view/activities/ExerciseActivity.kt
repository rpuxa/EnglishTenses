package ru.rpuxa.englishtenses.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.viewbinding.ViewBinding
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.view.fragments.ExerciseFragment

abstract class ExerciseActivity(private val tipMode: Boolean) : AppCompatActivity() {

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
            onResult(it)
        }
        showFragment(currentFragment)
    }

    abstract fun onResult(result: ExerciseResult)


    companion object {
        const val TENSES = "tenses"
    }
}