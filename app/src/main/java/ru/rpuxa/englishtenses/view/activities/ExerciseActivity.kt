package ru.rpuxa.englishtenses.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.viewbinding.ViewBinding
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.model.ExerciseResult
import ru.rpuxa.englishtenses.view.fragments.ExerciseFragment

abstract class ExerciseActivity(private val tipMode: Boolean) : AppCompatActivity() {

    abstract val binding: ViewBinding
    abstract val content: FragmentContainerView
    private lateinit var currentFragment: ExerciseFragment
    private val nextListener = object : (ExerciseResult) -> Unit {
        override fun invoke(result: ExerciseResult) {
            val old = currentFragment
            updateFragment()
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .remove(old)
                .add(R.id.content, currentFragment)
                .commit()
            currentFragment.setOnNextListener(this)
            onResult(result)
        }
    }
    protected val tenses by lazy { intent.extras?.get(TENSES) as Set<Int> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        updateFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, currentFragment)
            .commit()

        currentFragment.setOnNextListener(nextListener)
    }

    abstract fun onResult(result: ExerciseResult)

    fun updateFragment() {
        currentFragment = ExerciseFragment.create(tenses, tipMode)
    }

    companion object {
        const val TENSES = "tenses"
    }
}