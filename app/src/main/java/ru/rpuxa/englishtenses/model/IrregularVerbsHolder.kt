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
        _verbs = listOf(
            IrregularVerb(0, "be", "was/were", "been"),
            IrregularVerb(1, "do/does", "did", "done"),
            IrregularVerb(2, "write", "wrote", "written")
        )
    }
}