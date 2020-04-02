package english.tenses.practice.model

import english.tenses.practice.model.db.TranslateDao
import english.tenses.practice.model.db.TranslateEntity
import english.tenses.practice.model.server.YandexTranslatorServer
import java.io.IOException

class Translator(
    private val apiKey: String,
    private val server: YandexTranslatorServer,
    private val translateDao: TranslateDao
) {

    suspend fun translate(text: String): String? {
        try {
            var translate = translateDao.get(text)?.translate
            if (translate != null) return translate
            translate = server.getTranslate(text, "en-ru", apiKey).text
            translateDao.insert(TranslateEntity(text, translate ?: return null))
            return translate
        } catch (e: IOException) {
            return null
        }
    }
}