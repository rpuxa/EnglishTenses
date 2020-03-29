package english.tenses.practice.model

object TenseHandler {

    fun createWrongAnswer(
        tense: Tense,
        verb: String,
        subject: String,
        person: Person
    ): String {

        fun have() = when (person) {
            Person.ME, Person.YOU -> "have"
            Person.IT -> "has"
            else -> error(person)
        }

        when (tense) {
            Tense.PRESENT_SIMPLE -> {
                if (verb in MODAL_VERBS) {
                    return "$verb $subject".trim()
                }
                if (subject.isBlank()) {
                    if (person == Person.IT) return toPresentSimpleSFrom(verb)
                    return PRESENT_SIMPLE_FIRST_PERSON_VERBS_EXCEPTIONS[verb] ?: verb
                }
                return "${if (person == Person.IT) "does" else "do"} $subject $verb"
            }
            Tense.PRESENT_CONTINUOUS -> {
                val auxiliaryVerb = when (person) {
                    Person.ME -> "am"
                    Person.IT -> "is"
                    Person.YOU -> "are"
                    else -> error(person)
                }
                val begin = "$auxiliaryVerb $subject".trim()
                return "$begin ${addIng(verb)}"
            }
            Tense.PRESENT_PERFECT -> {
                val begin = "${have()} $subject".trim()
                return "$begin ${thirdForm(verb)}"
            }
            Tense.PRESENT_PERFECT_CONTINUOUS -> {
                val begin = "${have()} $subject".trim()
                return "$begin been ${addIng(verb)}"
            }
            Tense.PAST_SIMPLE -> {
                if (verb in MODAL_VERBS) {
                    return "$verb $subject".trim()
                }
                if (subject.isBlank()) {
                    return secondForm(verb, person)
                }
                return "did $subject $verb"
            }
            Tense.PAST_CONTINUOUS -> {
                val begin = "${secondForm("be", person)} $subject".trim()
                return "$begin ${addIng(verb)}"
            }
            Tense.PAST_PERFECT -> {
                val begin = "had $subject".trim()
                return "$begin ${thirdForm(verb)}"
            }
            Tense.PAST_PERFECT_CONTINUOUS -> {
                val begin = "had $subject".trim()
                return "$begin been ${addIng(verb)}"
            }
            Tense.FUTURE_SIMPLE -> {
                val begin = "will $subject".trim()
                return "$begin $verb"
            }
            Tense.FUTURE_CONTINUOUS -> {
                val begin = "will $subject".trim()
                return "$begin be ${addIng(verb)}"
            }
            Tense.FUTURE_PERFECT -> {
                val begin = "will $subject".trim()
                return "$begin have ${thirdForm(verb)}"
            }
            Tense.FUTURE_PERFECT_CONTINUOUS -> {
                val begin = "will $subject".trim()
                return "$begin have been ${addIng(verb)}"
            }
        }


    }

    private fun toPresentSimpleSFrom(verb: String): String {
        PRESENT_SIMPLE_S_VERBS_EXCEPTIONS[verb]?.let { return it }
        PRESENT_SIMPLE_ENDS.forEach {
            if (verb.endsWith(it))
                return verb + "es"
        }
        if (verb.length > 1 && verb[verb.lastIndex - 1].isConsonant() && verb.last() == 'y')
            return buildString {
                withoutLast(verb, 1)
                append("ies")
            }

        return "${verb}s"
    }

    private fun addIng(verb: String): String {
        ING_EXCEPTIONS[verb]?.let { return it }
        if (verb in MODAL_VERBS) {
            return verb
        }

        verb.doubleLastConsonant()?.let {
            return "${it}ing"
        }
        if (verb.endsWith("ie")) {
            return buildString {
                withoutLast(verb, 2)
                append("ying")
            }
        }
        if (verb.length > 3 && verb.last() == 'e')
            return buildString {
                withoutLast(verb, 1)
                append("ing")
            }

        return "${verb}ing"
    }

    private fun thirdForm(verb: String) = IrregularVerb.byFirst(verb)?.third?.first() ?: addEd(verb)
    private fun secondForm(verb: String, person: Person) =
        IrregularVerb.secondForm(verb, person) ?: addEd(verb)

    private fun addEd(verb: String): String {
        if (verb.last() == 'e')
            return "${verb}d"

        if (verb.length > 1 && verb[verb.lastIndex - 1].isConsonant() && verb.last() == 'y') {
            return buildString {
                withoutLast(verb, 1)
                append("ied")
            }
        }

        verb.doubleLastConsonant()?.let {
            return "${it}ed"
        }

        return "${verb}ed"
    }


    private fun Char.isVowel() =
        this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u'

    private fun Char.isConsonant() = !isVowel()
    private fun StringBuilder.withoutLast(string: String, n: Int) {
        for (i in 0 until string.length - n) {
            append(string[i])
        }
    }

    private fun String.doubleLastConsonant(): String? {
        if (this in NOT_DOUBLING_EXCEPTIONS) return null
        if (this !in DOUBLING_EXCEPTIONS && this[lastIndex - 1].isConsonant() || last().isVowel())
            return null
        return when (last()) {
            'h', 'w', 'x', 'y' -> this
            'c' -> this + 'k'
            else -> this + last()
        }
    }

    private val PRESENT_SIMPLE_ENDS = arrayOf(
        "o", "ch", "sh", "s", "x", "z"
    )
    private val MODAL_VERBS = setOf(
        "can", "could",
        "may", "might",
        "shall", "should",
        "will", "would",
        "must"
    )

    private val NOT_DOUBLING_EXCEPTIONS = setOf(
        "sleep",
        "shoot",
        "steal",
        "book",
        "open",
        "feed",
        "contain",
        "happen",
        "recover",
        "look",
        "meet",
        "feel",
        "sail",
        "postpone",
        "seem",
        "visit",
        "visit",
        "discover",
        "deliver",
        "wear",
        "roam",
        "eat",
        "discover",
        "open",
        "cook",
        "rain",
        "clean",
        "listen",
        "cook",
        "spent",
        "read",
        "seem",
        "happen",
        "recover",
        "wait",
        "load",
        "appear",
        "hear",
        "lead",
        "sail",
        "answer",
        "listen",
        "speak",
        "appear",
        "answer",
        "look",
        "postpone",
        "contain",
        "load",
        "clean",
        "deliver",
        "wait",
        "roam",
        "rain",
        "break",
        "heal",
        "train"
    )

    private val DOUBLING_EXCEPTIONS = setOf(
        "prefer"
    )

    private val PRESENT_SIMPLE_S_VERBS_EXCEPTIONS = mapOf(
        "have" to "has",
        "be" to "was"
    )

    private val PRESENT_SIMPLE_FIRST_PERSON_VERBS_EXCEPTIONS = mapOf(
        "be" to "were"
    )

    private val ING_EXCEPTIONS = mapOf(
        "use" to "using"
    )
}