package english.tenses.practice.model

enum class Languages(val code: String) {
    GERMAN("de"),
    NORWEGIAN("no"),
    RUSSIAN("ru"),
    BELARUSIAN("be"),
    FINNISH("fi"),
    PORTUGUESE("pt"),
    BULGARIAN("bg"),
    LITHUANIAN("lt"),
    LATVIAN("lv"),
    CROATIAN("hr"),
    FRENCH("fr"),
    HUNGARIAN("hu"),
    ARMENIAN("hy"),
    UKRAINIAN("uk"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    CATALAN("ca"),
    MACEDONIAN("mk"),
    ALBANIAN("sq"),
    SERBIAN("sr"),
    SWEDISH("sv"),
    GREEK("el"),
    ENGLISH("en"),
    ITALIAN("it"),
    SPANISH("es"),
    ESTONIAN("et"),
    CZECH("cs"),
    AZERBAIJANI("az"),
    POLISH("pl"),
    ROMANIAN("ro"),
    DANISH("da"),
    TURKISH("tr"),
    DUTCH("nl")
    ;

    companion object {
        operator fun get(code: String) = values().first { it.code == code }
    }
}