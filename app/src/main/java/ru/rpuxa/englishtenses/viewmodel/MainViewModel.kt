package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.SingleLiveEvent
import ru.rpuxa.englishtenses.State
import ru.rpuxa.englishtenses.event
import ru.rpuxa.englishtenses.model.Tense
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _chosen = State(HashSet<Int>())
    val chosen = _chosen.liveData

    val showTenseDialog = MutableLiveData<Tense>()

    fun changeState(id: Int) {
        _chosen.update {
            if (id in _chosen.value) {
                _chosen.value.remove(id)
            } else {
                _chosen.value.add(id)
            }
        }
    }

    fun choseAll() {
        _chosen.update {
            it.addAll(Tense.allCodes)
        }
    }

    fun clearAll() {
        _chosen.update {
            it.clear()
        }
    }

    fun tenseClick(id: Int) {
        if (_chosen.value.isEmpty()) {
            showTenseDialog.value = Tense[id]
        } else {
            changeState(id)
        }
    }

    fun changeAllState() {
        if (_chosen.value.size == Tense.values().size) {
            clearAll()
        } else {
            choseAll()
        }
    }
}