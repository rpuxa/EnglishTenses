package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.rpuxa.englishtenses.State
import ru.rpuxa.englishtenses.model.IrregularVerb
import javax.inject.Inject

class IrregularVerbsViewModel @Inject constructor(
) : ViewModel() {


    private val _verbs = State(IrregularVerb.values().toList())
    val verbs = _verbs.liveData

    fun searchFlow(flow: Flow<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            flow.debounce(100)
                .collect {
                    _verbs.value = if (it.isBlank()) {
                         IrregularVerb.values().toList()
                    } else {
                        val words = it.split(',', ' ')
                            .filterNot { it.isBlank() }
                            .map {
                                it.filterNot { it.isWhitespace() }
                            }

                        fun String.filter(): Boolean {
                            return words.any { contains(it, true) }
                        }
                         IrregularVerb.values().filter {
                            it.first.filter() || it.second.any { it.filter() } || it.third.any { it.filter() }
                        }
                    }
                }
        }
    }


}