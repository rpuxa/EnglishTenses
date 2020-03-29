package english.tenses.practice.parser.handler

import kotlinx.coroutines.CancellationException
import english.tenses.practice.model.IrregularVerb
import english.tenses.practice.model.Person
import english.tenses.practice.model.Tense
import english.tenses.practice.parser.*
import english.tenses.practice.parser.raw.RAW_FILE_NAMES
import english.tenses.practice.parser.raw.RawSentence
import java.io.*

const val HANDLED_FILE_NAME = "output/handle/handled"
const val CACHE_FILE = "output/handle/cache"

object Handler {
    @JvmStatic
    fun main(a: Array<String>) {
        println(
            "will children be playing".words().determineTense(null)
        )
    }
}

fun main() {
    val list =
        RAW_FILE_NAMES.flatMap { ObjectInputStream(FileInputStream(it)).use { it.readObject() as List<RawSentence> } }
    try {
        loadCache()
        val result = list.map { sentence ->
            val texts = sentence.text.split("%s")
            val textLists = texts.map { it.words() }
            val list = sentence.answers.mapIndexed { index, rawAnswer ->
                val maxBy = rawAnswer.forms.filter { '\'' !in it }.maxBy { it.length }!!
                val words = maxBy.words()
                require(words.all { it.isNotBlank() }) { maxBy }

                val answer = words.determineTense(sentence)
                answer.verb = rawAnswer.infinitive.words().last()
                answer.person = rawAnswer.person
                if (answer.verb !in allWords) {
                    System.err.println("Unknown word ${answer.verb}   $rawAnswer")
                }
                if (answer.verb !in popularWords) {
               //     System.err.println("Not popular word ${answer.verb}   $rawAnswer")
                }

                if (answer.verb in pretexts) {
                    System.err.println("Wrong verb: ${answer.verb}   $rawAnswer")
                }

                answer.simpleAnswer = rawAnswer
                answer.text = texts[index]
                answer
            }

            HandledSentence(
                sentence.text,
                list
            )
        }

        result.forEach { sentence ->
            sentence.answers.forEachIndexed { index, answer ->
                // answer.checkIfNeeded(sentence.text, index)
            }
        }

        ObjectOutputStream(FileOutputStream(HANDLED_FILE_NAME)).use {
            it.writeObject(result)
        }
    } finally {
        saveCache()
    }
}


lateinit var cacheTenses: HashMap<HandledAnswer, Person>
lateinit var cacheChecked: HashSet<HandledAnswer>

@Suppress("UNCHECKED_CAST")
fun loadCache() {
    try {
        ObjectInputStream(FileInputStream(CACHE_FILE)).use {
            cacheTenses = it.readObject() as HashMap<HandledAnswer, Person>
            cacheChecked = it.readObject() as HashSet<HandledAnswer>
        }
    } catch (e: IOException) {
        cacheTenses = HashMap()
        cacheChecked = HashSet()
    }
}

fun saveCache() {
    ObjectOutputStream(FileOutputStream(CACHE_FILE)).use {
        it.writeObject(cacheTenses)
        it.writeObject(cacheChecked)
    }
}

fun HandledAnswer.checkIfNeeded(sentenceText: String, index: Int) {
    if (!needsCheck)
        return
    if (this in cacheChecked)
        return


    println("\n\n\n\n\n\n\n")
    println(sentenceText)
    println("Index: $index")
    if (subject != null)
        println("Subject: $subject")
    println("Person $person")
    println("Tense $tense")
    println("0) skip")
    println("1) ME")
    println("2) YOU")
    println("3) IT")
    println("-1) exit with exception")

    val result: Person?
    loop@ while (true) {
        result = when (readLine()?.toIntOrNull()) {
            0 -> null
            1 -> Person.ME
            2 -> Person.YOU
            3 -> Person.IT
            -1 -> throw CancellationException()
            else -> {
                println("Try again")
                continue@loop
            }
        }
        break
    }

    if (result != null) {
        person = result
    }

    cacheChecked.add(this)
}


fun handleManually(answer: HandledAnswer) {
    println(answer)
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
    cacheTenses[answer.copy()] = result!!
    answer.person = result
    answer.needsCheck = false
}


fun List<String>.subject(from: Int, to: Int): String? {
    if (from >= size - to) return null
    return subList(from, size - to).joinToString(" ")
}

fun Char.isVowel() = this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u'
fun <T> List<T>.last(int: Int) = get(lastIndex - int)
fun CharSequence.last(int: Int) = get(lastIndex - int)
fun String.removeLast(int: Int) = substring(0, length - int)
fun CharSequence.words() =
    split(' ', ',', '.').filter { it.isNotBlank() }.map { it.trim().toLowerCase() }

fun String.determineSingleWord(): HandledAnswer {
    var verb: String? = null
    var irregularVerb: IrregularVerb? = null
    for (it in irregularVerbs) {
        verb = it.second.find { it == this }
        irregularVerb = it
        if (verb != null) break
    }

    if (verb != null && verb != irregularVerb!!.first) {
        return HandledAnswer(
            Tense.PAST_SIMPLE,
            irregularVerb.first,
            null,
            Person.UNKNOWN,
            true
        )
    }

    isSimpleVerb()?.let {
        return HandledAnswer(
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

    return HandledAnswer(
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
    irregularVerbs.forEach {
        if (it.third.any { it == this })
            return it.first
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

fun List<String>.determineTense(sentence: RawSentence?): HandledAnswer {
    fun wrongTense(): HandledAnswer {
        error("Unknown tense:   ${joinToString(" ")}    $sentence")
      /*  return HandledAnswer(
            Tense.FUTURE_PERFECT_CONTINUOUS,
            "",
            "",
            Person.UNKNOWN,
            true
        )*/
    }

    if (size == 1) {
        return first().determineSingleWord()
    }

    if (first().isFutureToBe()) {
        if (last().isContinuous()) {
            if (last(1) == "be") {
                return HandledAnswer(
                    Tense.FUTURE_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 2),
                    Person.UNKNOWN,
                    true
                )
            }

            if (last(1) == "been" && last(2) == "have") {
                return HandledAnswer(
                    Tense.FUTURE_PERFECT_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 3),
                    Person.UNKNOWN,
                    true
                )
            }
            return wrongTense()
        }

        val thirdForm = last().isThirdForm()
        return if (thirdForm != null && last(1) == "have") {
            HandledAnswer(
                Tense.FUTURE_PERFECT,
                thirdForm,
                subject(1, 2),
                Person.UNKNOWN,
                true
            )
        } else {
            HandledAnswer(
                Tense.FUTURE_SIMPLE,
                last(),
                subject(1, 1),
                Person.UNKNOWN,
                true
            )
        }
    }

    if (first() == "did") {
        return HandledAnswer(
            Tense.PAST_SIMPLE,
            last(),
            subject(1, 1) ?: return wrongTense(),
            Person.UNKNOWN,
            true
        )
    }

    if (last().isContinuous()) {
        first().isPastToBe()?.let {
            return HandledAnswer(
                Tense.PAST_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 1),
                it,
                it != Person.IT
            )
        }
        if (first() == "had") {
            return HandledAnswer(
                Tense.PAST_PERFECT_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 2),
                Person.UNKNOWN,
                true
            )
        }
        first().isPresentToHave()?.let {
            if (last(1) == "been") {
                return HandledAnswer(
                    Tense.PRESENT_PERFECT_CONTINUOUS,
                    last().removeContinuous(),
                    subject(1, 2),
                    it,
                    true
                )
            }
        }
        first().isPresentToBe()?.let {
            return HandledAnswer(
                Tense.PRESENT_CONTINUOUS,
                last().removeContinuous(),
                subject(1, 1),
                it,
                it != Person.IT
            )
        }

        return wrongTense()
    }

    val thirdForm = last().isThirdForm()
    if (first() == "had" && thirdForm != null)
        return HandledAnswer(
            Tense.PAST_PERFECT,
            thirdForm,
            subject(1, 1),
            Person.UNKNOWN,
            true
        )

    val third = last().isThirdForm()
    first().isPresentToHave()?.let {
        if (third != null) {
            return HandledAnswer(
                Tense.PRESENT_PERFECT,
                third,
                subject(1, 1),
                it,
                it == Person.FIRST
            )
        }
    }
    first().isPresentToDo()?.let {
        return HandledAnswer(
            Tense.PRESENT_SIMPLE,
            last(),
            subject(1, 1),
            it,
            it == Person.FIRST
        )
    }

    if (first() in modalVerbs) {
        return HandledAnswer(
            Tense.PAST_SIMPLE,
            last(),
            subject(1, 0) ?: return wrongTense(),
            Person.UNKNOWN,
            true
        )
    }

    return wrongTense()
}


fun HandledAnswer.determinePerson(sentence: List<String>): Person? {
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
