package english.tenses.practice.model.enums

enum class Language(val code: String) {
    GERMAN("de"),
    NORWEGIAN("no"),
    RUSSIAN("ru"),
    FINNISH("fi"),
    PORTUGUESE("pt"),
    BULGARIAN("bg"),
    LITHUANIAN("lt"),
    LATVIAN("lv"),
    CROATIAN("hr"),
    FRENCH("fr"),
    HUNGARIAN("hu"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    ALBANIAN("sq"),
    SERBIAN("sr"),
    SWEDISH("sv"),
    GREEK("el"),
    ENGLISH("en"),
    ITALIAN("it"),
    SPANISH("es"),
    ESTONIAN("et"),
    CZECH("cs"),
    POLISH("pl"),
    ROMANIAN("ro"),
    DANISH("da"),
    TURKISH("tr"),
    DUTCH("nl")
    ;

    companion object {
        operator fun get(code: String) = values().first { it.code == code }
        fun getOrNull(code: String) = values().find { it.code == code }
    }
}