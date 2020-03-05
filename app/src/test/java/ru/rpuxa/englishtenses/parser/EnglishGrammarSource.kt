package ru.rpuxa.englishtenses.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import ru.rpuxa.englishtenses.Sentence
import ru.rpuxa.englishtenses.WordAnswer

object EnglishGrammarSource : SentenceSource {

    override fun loadSentences(): List<Sentence> {
        val urlS =
            "https://www.english-grammar.at/online_exercises/tenses/present-simple-progressive6.htm"
        val doc = Jsoup.connect(urlS).get()
        val code = doc.select("script").first().data()
        val array =
            code.substring(code.indexOf("I = new Array();"), code.indexOf("State = new Array();"))

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

        return doc.select("div").filter { it.hasClass("ClozeBody") }[0]
            .select("ol")
            .flatMap {
                it.childNodes()
                    .map { node ->
                        node.childNodes().joinToString("") {
                            if (it is TextNode) {
                                it.text()
                            } else if (it is Element) {
                                if (it.select("span").isEmpty()) {
                                    it.text()

                                } else {
                                    "%s"
                                }
                            } else {
                                error("wrong type")
                            }
                        }
                    }
                    .filter { it.isNotBlank() }
            }
            .map {
                val answers = ArrayList<WordAnswer>()
                val infinitives = it.substring(it.lastIndexOf('(') + 1, it.lastIndexOf(')'))
                    .split(", ")
                    .iterator()
                var s = it
                while ("%s" in s) {
                    s = s.replaceFirst("%s", "")
                    answers.add(
                        WordAnswer(
                            infinitives.next(),
                            words[wordId++]
                        )
                    )
                }
                Sentence(
                    it.substring(0, it.lastIndexOf('(')).trim(),
                    answers
                )
            }
    }
}