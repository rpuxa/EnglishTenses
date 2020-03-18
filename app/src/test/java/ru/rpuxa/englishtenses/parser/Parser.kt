package ru.rpuxa.englishtenses.parser

import kotlinx.coroutines.CancellationException
import ru.rpuxa.englishtenses.model.Person
import ru.rpuxa.englishtenses.model.Tense
import java.io.*

val allWords = HashSet<String>()
val popularWords = run {
    val popularWords = HashSet<String>()
    File("output/en.txt").bufferedReader().lines().forEach {
        val (first, second) = it.split(' ')
        val score = second.toInt()
        if (score > 50)
            allWords.add(first)
        if (score > 5000)
            popularWords.add(first)
    }
    popularWords
}

const val HANDLED_ANS_FILE = "output/handled"
lateinit var handledAns: HashMap<Ans, Person>

@Suppress("UNCHECKED_CAST")
fun loadHandledAns() {
    try {
        ObjectInputStream(FileInputStream(HANDLED_ANS_FILE)).use {
            handledAns = it.readObject() as HashMap<Ans, Person>
        }
    } catch (e: IOException) {
        handledAns = HashMap()
    }
}

fun saveHandledAns() {
    ObjectOutputStream(FileOutputStream(HANDLED_ANS_FILE)).use {
        it.writeObject(handledAns)
    }
}

fun main() {
    try {
        loadHandledAns()
        val s = EnglishGrammarSource.loadSentences()
        s.toSentences().save("english grammar")
    } finally {
        saveHandledAns()
    }
}

fun List<SentenceToCheck>.save(name: String){
    ObjectOutputStream( FileOutputStream(File("output/examples", name))).use {
        it.writeObject(this)
    }
}

val irregularVerbs = loadIrregularVerbs()



data class Ans(
    val tense: Tense,
    val verb: String,
    val subject: String?,
    var person: Person,
    var needsCheck: Boolean,
    var text: String? = null
) : Serializable {
    var simpleAnswer: SimpleAnswer? = null
}

fun List<String>.subject(from: Int, to: Int): String? {
    if (from >= size - to) return null
    return subList(from, size - to).joinToString(" ")
}

fun Char.isVowel() = this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u'
fun <T> List<T>.last(int: Int) = get(lastIndex - int)
fun CharSequence.last(int: Int) = get(lastIndex - int)
fun String.removeLast(int: Int) = substring(0, length - int)
fun CharSequence.words() = split(' ').filter { it.isNotBlank() }.map { it.trim().toLowerCase() }

fun String.determineSingleWord(): Ans {
    var verb: String? = null
    var irregularVerb: IrregularVerb? = null
    for (it in irregularVerbs) {
        verb = it.second.find { it == this }
        irregularVerb = it
        if (verb != null) break
    }

    if (verb != null && verb != irregularVerb!!.first) {
        return Ans(
            Tense.PAST_SIMPLE,
            irregularVerb.first,
            null,
            Person.UNKNOWN,
            true
        )
    }

    isSimpleVerb()?.let {
        return Ans(
            Tense.PAST_SIMPLE,
            it,
            null,
            Person.UNKNOWN,
            true
        )
    }


    var person = Person.FIRST
    var word = this
    if (word.endsWith("es")) {
        word = word.removeLast(if (word.last(2).isVowel()) 2 else 1)
        person = Person.IT
    } else if (word.endsWith("s")) {
        word = word.removeLast(1)
        person = Person.IT
    }

    return Ans(
        Tense.PRESENT_SIMPLE,
        word,
        null,
        person,
        person == Person.FIRST
    )
}

fun String.isSimpleVerb(): String? {
    if (endsWith("ed")) {
        var word = removeLast(2)
        if (word.last() == word.last(1)) {
            word = word.substring(0, word.lastIndex)
        }
        return word
    }
    return null
}

fun String.isThirdForm(): String? {
    var verb: String? = null
    var irregularVerb: IrregularVerb? = null
    for (it in irregularVerbs) {
        verb = it.third.find { it == this }
        irregularVerb = it
        if (verb != null) break
    }

    if (verb != null && verb != irregularVerb!!.first) {
        return irregularVerb.first
    }

    return isSimpleVerb()
}

fun String.isFutureToBe() = this == "will" || this == "shall"
fun String.isPastToBe() = when (this) {
    "was" -> Person.IT
    "were" -> Person.FIRST
    else -> null
}

fun String.isPresentToBe() = when (this) {
    "am" -> Person.ME
    "is" -> Person.IT
    "are" -> Person.YOU
    else -> null
}

fun String.isPresentToDo() = when (this) {
    "do" -> Person.FIRST
    "does" -> Person.IT
    else -> null
}

fun String.isPresentToHave() = when (this) {
    "have" -> Person.FIRST
    "has" -> Person.IT
    else -> null
}


fun String.isContinuous() = endsWith("ing")

fun String.removeContinuous() = continuousVerbs[this] ?: removeLast(3)

fun List<String>.determineTense(): Ans {
    fun wrongTense(): Nothing = error("Unknown tense:   ${joinToString(" ")}")

    if (size == 1) {
        return first().determineSingleWord()
    }

    if (first().isFutureToBe()) {
        if (last().isContinuous()) {
            if (last(1) == "be") {
                return Ans(
                    Tense.FUTURE_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 1),
                    Person.UNKNOWN,
                    true
                )
            }

            if (last(1) == "been" && last(2) == "have") {
                return Ans(
                    Tense.FUTURE_PERFECT_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 3),
                    Person.UNKNOWN,
                    true
                )
            }
            wrongTense()
        }

        val thirdForm = last().isThirdForm()
        return if (thirdForm != null && last(1) == "have") {
            Ans(
                Tense.FUTURE_PERFECT,
                thirdForm,
                subject(1, 2),
                Person.UNKNOWN,
                true
            )
        } else {
            Ans(
                Tense.FUTURE_SIMPLE,
                last(),
                subject(1, 1),
                Person.UNKNOWN,
                true
            )
        }
    }

    if (first() == "did") {
        return Ans(
            Tense.PAST_SIMPLE,
            last(),
            subject(1, 1) ?: wrongTense(),
            Person.UNKNOWN,
            true
        )
    }

    if (last().isContinuous()) {
        first().isPastToBe()?.let {
            return Ans(
                Tense.PAST_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 1),
                it,
                it != Person.IT
            )
        }
        if (first() == "had") {
            return Ans(
                Tense.PAST_PERFECT_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 2),
                Person.UNKNOWN,
                true
            )
        }
        first().isPresentToHave()?.let {
            if (last(1) == "been") {
                return Ans(
                    Tense.PRESENT_PERFECT_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 2),
                    it,
                    true
                )
            }
        }
        first().isPresentToBe()?.let {
            return Ans(
                Tense.PRESENT_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 1),
                it,
                it != Person.IT
            )
        }

        wrongTense()
    }

    val thirdForm = last().isThirdForm()
    if (first() == "had" && thirdForm != null)
        return Ans(
            Tense.PAST_PERFECT,
            thirdForm,
            subject(1, 1),
            Person.UNKNOWN,
            true
        )

    val third = last().isThirdForm()
    first().isPresentToHave()?.let {
        if (third != null) {
            return Ans(
                Tense.PRESENT_PERFECT,
                third,
                subject(1, 1),
                it,
                it == Person.FIRST
            )
        }
    }
    first().isPresentToDo()?.let {
        return Ans(
            Tense.PRESENT_SIMPLE,
            last(),
            subject(1, 1),
            it,
            it == Person.FIRST
        )
    }

    wrongTense()
}


fun Ans.determinePerson(sentence: List<String>): Person? {
    if (person != Person.FIRST && person != Person.UNKNOWN) return null
    ((subject?.words() ?: emptyList()) + sentence.asReversed())
        .forEach {
            val word = it.toLowerCase()
            if (word == "i") {
                needsCheck = false
                return if (person == Person.FIRST) Person.ME else Person.UNKNOWN
            }
            when (word) {
                "he", "she", "it" -> return if (person == Person.FIRST) Person.UNKNOWN else Person.IT
                "they", "you", "we" -> return if (person == Person.FIRST) Person.YOU else Person.UNKNOWN
            }
        }

    return Person.UNKNOWN
}

fun List<SimpleSentence>.toSentences(): List<SentenceToCheck> {
    return map { sentence ->
        val texts = sentence.text.split("%s")
        val textLists = texts.map { it.words() }
        val list = sentence.answers.mapIndexed { index, answer ->
            val maxBy = answer.forms.filter { '\'' !in it }.maxBy { it.length }!!
            val words = maxBy.words()
            require(words.all { it.isNotBlank() }) { maxBy }

            val tense = words.determineTense()
            require(tense.verb in allWords) { "Unknown word  ${tense.verb}" }
            if (tense.verb !in popularWords) {
                System.err.println("Not popular word! ${tense.verb}")
            }
            tense.simpleAnswer = answer
            tense.text = texts[index]
            tense.determinePerson(textLists[index])?.let { tense.person = it }
            val person = handledAns[tense]
            if (person != null) {
                tense.person = person
            } else if (tense.person == Person.UNKNOWN || tense.person == Person.FIRST) {
                println(tense)
                println("1) ME")
                println("2) YOU")
                println("3) IT")
                println("0) exit with exception")
                val result: Person?
                loop@ while (true) {
                    result = when (readLine()?.toIntOrNull()) {
                        1 -> Person.ME
                        2 -> Person.YOU
                        3 -> Person.IT
                        0 -> throw CancellationException()
                        else -> {
                            println("Try again")
                            continue@loop
                        }
                    }
                    break
                }
                handledAns[tense.copy()] = result!!
                tense.person = result
                tense.needsCheck = false
            }
            println("$answer       <------>      $tense")
            tense
        }
        SentenceToCheck(
            sentence.text,
            list
        )
    }
}

