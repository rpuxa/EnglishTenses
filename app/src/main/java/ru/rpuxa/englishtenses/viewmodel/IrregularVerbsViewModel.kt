package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.model.IrregularVerb
import ru.rpuxa.englishtenses.model.IrregularVerbsHolder
import javax.inject.Inject

class IrregularVerbsViewModel @Inject constructor(
    private val irregularVerbsHolder: IrregularVerbsHolder
) : ViewModel() {

    val verbs = irregularVerbsHolder.verbs


}