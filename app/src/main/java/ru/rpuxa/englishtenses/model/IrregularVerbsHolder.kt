package ru.rpuxa.englishtenses.model

import android.content.Context

class IrregularVerbsHolder(
    private val context: Context
) {

    private var _verbs: List<IrregularVerb>? = null

    val verbs: List<IrregularVerb>
        get() {
            if (_verbs == null)
                preload()
            return _verbs!!
        }

    fun preload() {
        _verbs = listOf()
    }
}