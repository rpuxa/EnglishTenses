package english.tenses.practice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.logic.RemoteSentenceLoader
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadViewModel @Inject constructor(
    private val loader: RemoteSentenceLoader,
    private val prefs: Prefs
) : ViewModel() {

    private val _progress = MutableLiveData<Float>()

    val progress: LiveData<Float> get() = _progress

    fun load() {
        if (prefs.sentencesHash.isEmpty())
            _progress.value = 1 / 5f
        viewModelScope.launch {
            loader.load().collect {
                _progress.value = it
            }
        }
    }
}