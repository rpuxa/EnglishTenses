package english.tenses.practice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.enums.Language
import english.tenses.practice.model.logic.Translator
import java.util.*
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val prefs: Prefs,
    private val translator: Translator
) : ViewModel() {

    private val sortedLanguages = Language.values().sortedBy { it.name }
    private val _nativeLanguageAutoDetection = MutableLiveData<Boolean>()
    private val _nativeLanguage = MutableLiveData<Int>()


    val nativeLanguageAutoDetection: LiveData<Boolean> get() = _nativeLanguageAutoDetection
    val nativeLanguage: LiveData<Int> get() = _nativeLanguage
    val languages = sortedLanguages.map {
        buildString {
            append(it.name.first())
            append(it.name.toLowerCase(), 1, it.name.length)
        }
    }


    init {
        _nativeLanguageAutoDetection.value = prefs.nativeLanguageAutoDetection
        val prefsLanguage = prefs.nativeLanguage
        _nativeLanguage.value = sortedLanguages.indexOfFirst {
            it.code == prefsLanguage
        }
    }

    fun languageSelected(position: Int) {
        val language = sortedLanguages[position]
        _nativeLanguage.value = position
        prefs.nativeLanguage = language.code
    }

    fun onAutoNativeLanguage(bFlag: Boolean) {
        _nativeLanguageAutoDetection.value = bFlag
        prefs.nativeLanguageAutoDetection = bFlag
        if (bFlag) {
            val language = Locale.getDefault().language
            languageSelected(sortedLanguages.indexOfFirst { it.code == language })
        }
    }

    override fun onCleared() {
        translator.load(Language[prefs.nativeLanguage])
    }
}