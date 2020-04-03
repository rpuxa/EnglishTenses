package english.tenses.practice.model.logic

import android.util.Log
import english.tenses.practice.loadDataBase
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.db.dao.TranslatesDao
import english.tenses.practice.model.db.entity.TranslateEntity
import english.tenses.practice.model.enums.Language
import english.tenses.practice.nextString
import kotlinx.coroutines.*

class Translator(
    private val prefs: Prefs,
    private val translatesDao: TranslatesDao
) {

    private var loader: Job? = null

    suspend fun translate(sentenceId: Int): String {
        val language = Language[prefs.nativeLanguage]
        load(language).join()
        return translatesDao.get(language, sentenceId).text
    }

    fun load(language: Language): Job {
        loader?.cancel()
        val loader = GlobalScope.launch {
            try {
                if (language == Language.ENGLISH || translatesDao.getRandom(language) != null) {
                    return@launch
                }
                updateTranslations(language, false)
            } catch (e: CancellationException){
                Log.d(TAG, "Cancelled $language")
            }
        }
        this.loader = loader
        return loader
    }

    suspend fun updateTranslations(language: Language, clear: Boolean) {
        Log.d(TAG, "Updating $language")
        val translates = loadDataBase(language.code).toTranslateEntities(language)
        translatesDao.update(translates, clear)
        Log.d(TAG, "Done $language!")
    }

    private fun String.toTranslateEntities(language: Language): List<TranslateEntity> {
        val iterator = iterator()
        val builder = StringBuilder()
        val translates = ArrayList<TranslateEntity>()
        while (iterator.hasNext()) {
            val id = iterator.nextString(builder, '#').toInt()
            val text = iterator.nextString(builder, '#')
            translates += TranslateEntity(
                id, language, text
            )
        }
        return translates
    }

    companion object {
        private const val TAG = "TranslatorDebug"
    }
}