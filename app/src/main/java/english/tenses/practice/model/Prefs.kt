package english.tenses.practice.model

import com.chibatching.kotpref.KotprefModel

class Prefs : KotprefModel() {
    var dismissRate by booleanPref(false)
    var appOpens by intPref()
}