/*
package english.tenses.englishtenses.parser.raw

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import english.tenses.englishtenses.parser.handler.words

object EnglishGrammarSource {

    @JvmStatic
    fun main(a: Array<String>) {
        val table =
            Jsoup.connect("https://www.english-grammar.at/online_exercises/tenses/tenses_index.htm")
                .get()
                .select("div")
                .first { it.className() == "col-md-9 column" }

        table.children().select("ul")
            .forEach {
                it.children().forEach {
                    val child = it.child(0)
                    val link =
                        "https://www.english-grammar.at/online_exercises/tenses/" + child.attr("href")
                    println("\"$link\",")
                }
            }
    }

    val links = arrayOf(
        "https://www.english-grammar.at/online_exercises/tenses/t127-present-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t095-present-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t080-present-simple-progressive.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t075-present-simple-continuous.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t068-past-tense-simple.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t067-present-simple-progressive.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t065-present-simple-progressive.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t058-present-simple-progressive.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t054-global-warming.htm",
        "https://www.english-grammar.at/online_exercises/tenses/wheres-linda.htm",
        "https://www.english-grammar.at/online_exercises/tenses/preserve-our-town-present-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/rex-our-dog-present-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/tekking_journey.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive10.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive09.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive08.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive07.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive6.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive5.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive4.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present_simple_progressive3.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present_simple_progressive2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present_simple_progressive1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t124-past-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t105-past-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t101-the-wrong-person.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t100-past-continuous-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t086-past-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t084-holidays-in-greece.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t081-past-simple-progressive.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t064-toms-double.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t063-past-continuous.htm",
        "https://www.english-grammar.at/online_exercises/tenses/pasttense2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/kidnapped-by-a-ufo.htm",
        "https://www.english-grammar.at/online_exercises/tenses/past-simple-progressive3.htm",
        "https://www.english-grammar.at/online_exercises/tenses/past-simple-progressive2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/past-simple-progressive1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t125-present-perfect-simple.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t118-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t117-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t087-present-perfect-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t083-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t077-present-perfect-simple-continuous.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t076-present-perfect-simple-continuous.htm",
        "https://www.english-grammar.at/online_exercises/tenses/present-perfect1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t073-present-perfect-continuous.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t129-mira-and-i.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t115-past-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t108-present-perfect-past.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t106-my-little-sister.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t104-predictions-of-the-future.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t098-past-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t096-past-simple-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t078-letter-to-ellen.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t070-past-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/for-since2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/for-since.htm",
        "https://www.english-grammar.at/online_exercises/tenses/past-present-perfect-1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t110-past-past-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t109-past-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t093-past-past-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t079-past-past-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t061-hurt-foot-past-past-perfect-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t057-past-past-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t126-future-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t122-future-forms.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t069-future-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t060-wind-farms.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t059-homes-of-the-future.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t128-going-fishing.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t119-job-offer.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t116.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t114-mixed-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t113-growing-up-two-cultures.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t112-life-as-a-model.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t111-back-to-germany.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t107-present-present-perfect.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t099-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t097-stranger-in-the-car.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t094-letter-from-paris.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t091-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t090-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t089-lost-in-the-mountains.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t085-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t082-meeting-a-family-friend.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t074-narrative-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t072-once-famous-film-star.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t071-the-lost-passport.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t066-our-neighbours.htm",
        "https://www.english-grammar.at/online_exercises/tenses/at056-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/at055-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/at053-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/at052-all-tenses.htm",
        "https://www.english-grammar.at/online_exercises/tenses/at050-my-piano-lessons.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses20.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses-19.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses18.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses17.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses16.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses15.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses14.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses13.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses12.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses11.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses10.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses9.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses8.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses7.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all-tenses6.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses5.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses4.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses3.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/all_tenses1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t123-past-tense-irregular-verbs.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t092-irregular-verbs-past-tense.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs6.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs5.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs4.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs3.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs2.htm",
        "https://www.english-grammar.at/online_exercises/tenses/irregularverbs1.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t121-time-expressions.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t120-state-action-verbs.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t103-nouns-verb-forms.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t102-just-already-yet.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t088-time-expressions.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t051-let-make-have.htm",
        "https://www.english-grammar.at/online_exercises/tenses/t062-time-expressions.htm"
    )

    val masks = arrayOf(
        0b111_1111_1110_0000_0110_1111L, 0

    )

    //https://www.english-grammar.at/online_exercises/tenses/tenses_index.htm
    fun loadSentences(urlS: String): List<RawSentence> {
        val doc = Jsoup.connect(urlS).get()
        val code = doc.select("script").first().data()
        val startIndex = code.indexOf("I = new Array();")
        val endIndex = code.indexOf("State = new Array();")
        if (startIndex < 0 || endIndex < 0) {
            println("Array not found  $urlS")
            return emptyList()
        }
        val array = code.substring(startIndex, endIndex)

        var list = array.split("\n\n")
        list = list.subList(1, list.lastIndex)
        val words = list.map {
            it.split('\'')
                .filterIndexed { index, s -> index % 2 == 1 }
                .dropLast(1)
                .map {
                    it.split("\\u").filter { it.isNotBlank() }
                        .map { it.toLong(16).toInt().toChar() }
                        .joinToString("")
                }
        }

        var wordId = 0

        val select = doc.select("div").filter { it.hasClass("ClozeBody") }[0]
            .select("li")
        return select
            .map {
                val string = it.childNodes().joinToString("") {
                    if (it is TextNode) {
                        it.text()
                    } else if (it is Element) {
                        if (it.select("span").isEmpty()) {
                            it.text()

                        } else {
                            "%s"
                        }
                    } else {
                        error("wrong type  $urlS")
                    }
                }
                if (string.isBlank())
                    error("Blank")
                string
            }.mapNotNull { sourceString ->
                var string = sourceString
                val answers = ArrayList<RawAnswer>()
                var infinitivesList = mutableListOf<String>()
                while (true) {
                    val bracketStart = string.lastIndexOf('(') + 1
                    val bracketEnd = string.lastIndexOf(')')
                    if (bracketStart == -1 || bracketEnd == -1) {
                        break
                    }
                    infinitivesList.add(string.substring(bracketStart, bracketEnd))
                    string =
                        string.substring(0, bracketStart - 1) + string.substring(bracketEnd + 1)
                }
                var s = string
                var count = 0
                while ("%s" in s) {
                    s = s.replaceFirst("%s", "")
                    count++
                }
                if (count == 0) {
                    println("Count 0")
                    println(sourceString)
                    return@mapNotNull null
                }
                if (count > 1 && infinitivesList.size == 1) {
                    infinitivesList = ArrayList(infinitivesList.first().split(','))
                    if (infinitivesList.size == 1) {
                        infinitivesList = MutableList(count) { infinitivesList.first() }
                    }
                }


                val infinitives = infinitivesList.iterator()
                s = string
                while ("%s" in s) {
                    s = s.replaceFirst("%s", "")
                    try {
                        answers.add(
                            RawAnswer(
                                infinitives.next().toLowerCase().trim(),
                                words[wordId++]
                            )
                        )
                    } catch (e: NoSuchElementException) {
                        println(urlS)
                        println(sourceString)
                        throw e
                    }
                }
                if (infinitives.hasNext()) {
                    println("Excess element $sourceString")
                    return@mapNotNull null
                }
                RawSentence(
                    string.trim(),
                    answers
                )
            }
    }

    fun loadAll(): List<RawSentence> {
        val result = ArrayList<RawSentence>()
        var j = 0
        for (i in masks.indices step 2) {
            var mask = masks[i]
            val zeros = masks[i + 1]
            while (mask != 0L) {
                if (mask and 1L == 1L) {
                    result += loadSentences(links[j])
                }
                j++
                mask = mask ushr 1
            }
            j += zeros.toInt()
        }
        return result
    }
}

fun main() {
    EnglishGrammarSource.loadSentences("https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive10.htm")
}*/
