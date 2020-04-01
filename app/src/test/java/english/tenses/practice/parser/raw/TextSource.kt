package english.tenses.practice.parser.raw

import english.tenses.practice.model.*
import java.io.File
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

object TextSource {

    val textsString = File("output/texts.txt")
        .inputStream()
        .readBytes()
        .toString(Charset.defaultCharset())
    val testsString = File("output/tests.txt")
        .inputStream()
        .readBytes()
        .toString(Charset.defaultCharset())

    val regexTexts = Regex(
        "(\\r\\n){4,}|(_+)|(\\((.+?)\\))|(\\.)|([,\\-“”?!:’])|(((?=[^.])[A-Za-z0-9])+)|(\\s+)",
        RegexOption.DOT_MATCHES_ALL
    )
    val regexTests =
        Regex(
            "(\\r\\n){4,}|(_+)|(\\((.+?)\\))|(\\d+\\.)|([,\\-“”?!:’.])|(((?=[^.])[A-Za-z0-9])+)|(\\s+)",
            RegexOption.DOT_MATCHES_ALL
        )

    val textsLexemes = regexTexts.findAll(textsString).mapNotNull {
        val values = it.groupValues
        when {
            values[1].isNotEmpty() -> NewText
            values[2].isNotEmpty() -> SpaceAnswer
            values[4].isNotEmpty() -> Answer(values[4])
            values[5].isNotEmpty() -> Point
            values[6].isNotEmpty() -> PunctuationMark(values[6])
            values[7].isNotEmpty() -> Word(values[7])
            values[9].isNotEmpty() -> WhiteSpace
            else -> null
        }
    }

    val testsLexemes = regexTests.findAll(testsString).mapNotNull {
        val values = it.groupValues
        when {
            values[1].isNotEmpty() -> NewText
            values[2].isNotEmpty() -> SpaceAnswer
            values[4].isNotEmpty() -> Answer(values[4])
            values[5].isNotEmpty() -> Point
            values[6].isNotEmpty() -> PunctuationMark(values[6])
            values[7].isNotEmpty() -> Word(values[7])
            values[9].isNotEmpty() -> WhiteSpace
            else -> null
        }
    }

    val lexemes = testsLexemes + textsLexemes

    fun parse(): List<RawSentence> {
        val lexemesIterator = lexemes.iterator()
        fun take(): List<TextElement>? {
            val list = ArrayList<TextElement>()
            while (lexemesIterator.hasNext()) {
                val next = lexemesIterator.next()
                if (next == NewText)
                    return list
                list.add(next)
            }
            return null
        }

        val sentences = ArrayList<RawSentence>()

        while (true) {
            val first = take() ?: break
            val second = take()!!
            val asterisks = determine(
                first.filter { it is Word || it is SpaceAnswer || it is Point || it is NewText } + Point,
                second.filter { it is Word || it is SpaceAnswer || it is Point || it is NewText } + Point
            )?.iterator() ?: kotlin.run {
                println(first.joinToString(" ") + "\n\n\n" + second.joinToString(" "))
                error("Wrong determine")
            }

            val stack = Stack<TextElement>()
            first.forEach {
                when (it) {
                    is SpaceAnswer -> {
                        val f = FullAnswer()
                        f.correct = asterisks.next().text()
                        stack.add(f)
                    }
                    is Answer -> {
                        val words = it.text.split(",").map { it.trim() }
                        val wordsIterator = words.reversed().iterator()
                        val count = stack.count { it is FullAnswer && it.infinitive == null }
                        val resultIterator = stack.reversed().iterator()
                        while (resultIterator.hasNext() && wordsIterator.hasNext()) {
                            val answer = resultIterator.next() as? FullAnswer ?: continue
                            if (count == 1) {
                                answer.infinitive = words.joinToString(" ")
                                break
                            }
                            answer.infinitive = wordsIterator.next()
                        }
                        require(count == 1 || !wordsIterator.hasNext())
                    }
                    else -> stack += it
                }
            }

            val textElements = ArrayList<TextElement>()
            stack.forEach {
                if (textElements.lastOrNull() is FullAnswer) {
                    textElements.add(WhiteSpace)
                }
                if (it is FullAnswer && textElements.isNotEmpty() && textElements.last() != WhiteSpace) {
                    textElements.add(WhiteSpace)
                }
                if (it != WhiteSpace || textElements.lastOrNull() != WhiteSpace)
                    textElements.add(it)
            }
            textElements.add(NewText)

            val sentence = ArrayList<String>()
            val answers = ArrayList<RawAnswer>()
            textElements.forEach {
                if (it is Point || it is NewText) {
                    if (sentence.isNotEmpty() && answers.isNotEmpty()) {
                        sentences += RawSentence(
                            sentence.joinToString(""),
                            answers.clone() as ArrayList<RawAnswer>
                        )
                    }
                    sentence.clear()
                    answers.clear()
                } else {
                    sentence += it.toString()
                    if (it is FullAnswer) {
                        val infinitive = it.infinitive!!.trim()
                        val substring = infinitive.substring(0, infinitive.length - 1)
                        require(substring.all { !it.isDigit() })
                        val person = when (val c = infinitive.last()) {
                            '1' -> Person.ME
                            '2' -> Person.YOU
                            '3' -> Person.IT
                            else -> error("Unknown person $infinitive  $c")
                        }
                        answers += RawAnswer(person, substring, listOf(it.correct))
                    }
                }
            }

        }

        return sentences
    }

    fun List<TextElement>.text(): String =
        joinToString(" ")


    fun determine(mask: List<TextElement>, sequence: List<TextElement>): List<List<TextElement>>? {
        var i = 0
        var j = 0
        while (true) {
            if (i == sequence.size || j == mask.size) {
                return if (i == sequence.size && j == mask.size) emptyList() else null
            }
            if (mask[j] == SpaceAnswer) {
                val begin = i
                while (true) {
                    val subSequence = sequence.subList(++i, sequence.size)
                    if (subSequence.isEmpty()) return null
                    determine(
                        mask.subList(j + 1, mask.size),
                        subSequence
                    )?.let {
                        val result = ArrayList<List<TextElement>>()
                        result.add(sequence.subList(begin, i))
                        result.addAll(it)
                        return result
                    }
                }
            }
            val textElement = mask[j++]
            val textElement1 = sequence[i++]
            if (textElement != textElement1) {
                if (textElement is Word && textElement1 is Word && textElement.text.equals(
                        textElement1.text,
                        true
                    )
                ) {
                    System.err.println("EQUALS  $textElement  $textElement1")
                }
                return null
            }
        }
    }
}

sealed class TextElement

object WhiteSpace : TextElement() {
    override fun toString() = " "
}

object SpaceAnswer : TextElement() {
    override fun toString() = "______"
}

object NewText : TextElement()
object Point : TextElement() {
    override fun toString() = "."
}

data class Word(var text: String) : TextElement() {
    override fun toString() = text
}

data class Answer(val text: String) : TextElement() {
    override fun toString() = "($text)"
}

data class PunctuationMark(val char: String) : TextElement() {
    override fun toString() = char
}

class FullAnswer : TextElement() {
    var infinitive: String? = null
    lateinit var correct: String

    override fun toString() = "%s"
}

fun main() {
    println( Tense.values().map {
        TenseHandler.createWrongAnswer(
            it,
            "be",
            "",
            Person.IT
        )
    }.joinToString(", ")
    )
}