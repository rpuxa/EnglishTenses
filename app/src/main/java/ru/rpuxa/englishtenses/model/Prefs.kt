package ru.rpuxa.englishtenses.model

import com.chibatching.kotpref.KotprefModel

class Prefs : KotprefModel() {

    var learnedSentences: LearnedSentences
        get() {
            return LearnedSentences(learnedSentencesString)
        }
        set(value) {
            learnedSentencesString = value.toString()
        }

    private var learnedSentencesString by stringPref()
}