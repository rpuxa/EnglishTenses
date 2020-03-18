package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.model.IrregularVerb
import javax.inject.Inject

class IrregularVerbsViewModel @Inject constructor(
) : ViewModel() {

    val verbs = IrregularVerb.values().filter { true } // TODO filter


}