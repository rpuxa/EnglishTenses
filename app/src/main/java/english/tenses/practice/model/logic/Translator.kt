package english.tenses.practice.model.logic

import java.io.IOException

class Translator {

    suspend fun translate(text: String): String? {
        return try {

            null
        } catch (e: IOException) {
            null
        }
    }
}