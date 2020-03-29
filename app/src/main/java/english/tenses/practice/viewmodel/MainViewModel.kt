package english.tenses.practice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import english.tenses.practice.State
import english.tenses.practice.model.Tense
import english.tenses.practice.model.db.CorrectnessStatisticDao
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val correctnessStatisticDao: CorrectnessStatisticDao
) : ViewModel() {

    private val _chosen = State(HashSet<Int>())
    val chosen = _chosen.liveData
    val correctness = correctnessStatisticDao.liveData
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