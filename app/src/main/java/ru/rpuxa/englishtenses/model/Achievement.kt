package ru.rpuxa.englishtenses.model

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import ru.rpuxa.englishtenses.R
import ru.rpuxa.englishtenses.view.App
import javax.inject.Inject

sealed class Achievement(
    val id: Int,
    progress: Int,
    val steps: List<Int>
) {
    @Inject
    lateinit var context: Context

    var progress = progress
        private set

    init {
        App.component.inject(this)
    }

    abstract fun subtitle(): String
    abstract fun title(): String

    fun starsCount(): Int {
        var count = 0
        for (it in steps) {
            if (it > progress) {
                break
            }
            count++
        }
        return count
    }

    fun setProgress(p: Int): String? {
        val title = title()
        val startCount = starsCount()
        progress = p
        return if (starsCount() > startCount) title else null
    }

    companion object {
        fun create(id: Int, progress: Int): Achievement = when (id) {
            OpenTheoryAchievement.ID -> OpenTheoryAchievement(progress)
            RateAppAchievement.ID -> RateAppAchievement(progress)
            LearnedSentencesAchievement.ID -> LearnedSentencesAchievement(progress)
            ComboTestAchievement.ID -> ComboTestAchievement(progress)
            EntireCorrectTestAchievement.ID -> EntireCorrectTestAchievement(progress)
            PassTestAchievement.ID -> PassTestAchievement(progress)
            else -> error("Unknown achievement $id")
        }

        val IDS = OpenTheoryAchievement.ID..PassTestAchievement.ID
    }
}

abstract class CountAchievement(
    id: Int,
    progress: Int,
    steps: List<Int>,
    private val title: Int,
    @StringRes
    private val subtitle: Int,
    private val plurals: Boolean = true
) : Achievement(id, progress, steps) {

    private fun nextStep() = steps.filter { it > progress }.min() ?: steps.last()

    override fun title(): String {
        val nextStep = nextStep()
        return if (plurals) {
            context.resources.getQuantityString(title, nextStep, nextStep)
        } else {
            context.getString(title, nextStep)
        }
    }

    override fun subtitle(): String {
        return context.getString(subtitle, progress)
    }
}

abstract class BooleanAchievement(
    id: Int,
    progress: Int,
    @StringRes
    private val title: Int
) : Achievement(id, progress, listOf(1)) {
    override fun title(): String {
        return context.getString(title)
    }

    override fun subtitle(): String = ""
}



class OpenTheoryAchievement(
    progress: Int
) : BooleanAchievement(
    ID,
    progress,
    R.string.open_theory
) {
    companion object {
        const val ID = 1
    }
}

class RateAppAchievement(
    progress: Int
) : BooleanAchievement(
    ID,
    progress,
    R.string.rate_app
) {
    companion object {
        const val ID = 2
    }
}

class LearnedSentencesAchievement(
    progress: Int
): CountAchievement(
    ID,
    progress,
    listOf(10, 50, 100, 250, 500, 1000, 5000),
    R.string.learn_sentences,
    R.string.learned,
    false
) {
    companion object {
        const val ID = 3
    }
}

class ComboTestAchievement(
    progress: Int
): CountAchievement(
    ID,
    progress,
    listOf(2, 4, 6, 8, 10),
    R.plurals.combo_test,
    R.string.passed
) {
    companion object {
        const val ID = 4
    }
}

class EntireCorrectTestAchievement(
    progress: Int
): CountAchievement(
    ID,
    progress,
    listOf(1, 3, 6, 9, 15),
    R.plurals.pass_test_without_mistakes,
    R.string.written
) {
    companion object {
        const val ID = 5
    }
}

class PassTestAchievement(
    progress: Int
) : CountAchievement(
    ID,
    progress,
    listOf(1, 5, 10, 25, 50, 100),
    R.plurals.pass_test,
    R.string.passed
) {
    companion object {
        const val ID = 6
    }
}