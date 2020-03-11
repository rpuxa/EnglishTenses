package ru.rpuxa.englishtenses.model

import android.content.Context

class User(
    private val prefs: Prefs
) {

   private var _sentences: LearnedSentences? = null
    val sentences: LearnedSentences
        get() {
            if (_sentences ==null){
                load()
            }
            return _sentences!!
        }

    fun load() {
        _sentences = prefs.learnedSentences
    }
}