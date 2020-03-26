package ru.rpuxa.englishtenses.model

import com.chibatching.kotpref.KotprefModel

class Prefs : KotprefModel() {
    var dismissRate by booleanPref(false)
}