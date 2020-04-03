package english.tenses.practice.model.db

import com.chibatching.kotpref.KotprefModel
import english.tenses.practice.model.enums.Language
import java.util.*

class Prefs : KotprefModel() {
    var dismissRate by booleanPref(false)
    var appOpens by intPref()
    var sentencesHash by stringPref("")
    var nativeLanguage by stringPref((Language.getOrNull(Locale.getDefault().language) ?: Language.ENGLISH).code)
    var nativeLanguageAutoDetection by booleanPref(true)
}