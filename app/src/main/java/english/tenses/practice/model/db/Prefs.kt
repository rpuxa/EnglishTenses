package english.tenses.practice.model.db

import com.chibatching.kotpref.KotprefModel

class Prefs : KotprefModel() {
    var dismissRate by booleanPref(false)
    var appOpens by intPref()
    var sentencesHash by stringPref("")
}