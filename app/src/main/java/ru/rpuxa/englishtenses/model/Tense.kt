package ru.rpuxa.englishtenses.model

import androidx.annotation.StringRes
import ru.rpuxa.englishtenses.R


// https://dictionary.cambridge.org/grammar/british-grammar/tenses-and-time
enum class Tense(val code: Int, @StringRes val stringRes: Int) {
    PAST_SIMPLE(0, R.string.past_simple),
    PRESENT_SIMPLE(1, R.string.present_simple),
    FUTURE_SIMPLE(2, R.string.future_simple),

    PAST_CONTINUOUS(3, R.string.past_continuous),
    PRESENT_CONTINUOUS(4, R.string.present_continuous),
    FUTURE_CONTINUOUS(5, R.string.future_continuous),


    PAST_PERFECT(6, R.string.past_perfect),
    PRESENT_PERFECT(7, R.string.present_perfect),
    FUTURE_PERFECT(8, R.string.future_perfect),

    PAST_PERFECT_CONTINUOUS(9, R.string.past_perfect_continuous),
    PRESENT_PERFECT_CONTINUOUS(10, R.string.present_perfect_continuous),
    FUTURE_PERFECT_CONTINUOUS(11, R.string.future_perfect_continuous),
    ;


    companion object {
        operator fun get(code: Int): Tense = values().first { it.code == code }
        val allCodes = 0..11
    }
}