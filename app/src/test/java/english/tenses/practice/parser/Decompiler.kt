/*
package english.tenses.practice.parser

import english.tenses.practice.model.enums.Person
import english.tenses.practice.model.enums.Tense
import english.tenses.practice.model.UnhandledAnswer
import english.tenses.practice.model.UnhandledSentence
import java.io.*

fun main() {
    val sentences = ArrayList<UnhandledSentence>()
    try {
        DataInputStream(FileInputStream("output/compiler/sentences.data")).use { input ->
            while (true) {
                sentences += UnhandledSentence(
                    input.readUTF(),
                    List(input.readByte().toInt()) {
                        UnhandledAnswer(
                            input.readUTF(),
                            List(input.readByte().toInt()) {
                                input.readUTF()
                            },
                            Tense[input.readByte().toInt()],
                            input.readUTF(),
                            input.readUTF(),
                            Person[input.readByte().toInt()]
                        )
                    }
                )
            }
        }
    } catch (e: EOFException) {
    }

    val oldIds = Array(Tense.values().size) { ArrayList<Int>() }

    val group = ArrayList<UnhandledSentence>()
    var newId = 1



    for ((index, sentence) in sentences.withIndex()) {
        group += sentence
        if (index % 19 == 0 && index != 0 || index == sentences.lastIndex) {
            val groupWithId = group.map { it to newId++ }
            groupWithId.forEach { (sentence, id) ->
                var text = sentence.text
                sentence.answers.forEach {
                    text = text.replaceFirst(
                        "%s",
                        "____ (${it.infinitive}${it.person.c})"
                    )
                }
                if (text.last().isLetter()) {
                    text += '.'
                }

                sentence.answers.forEach {
                    oldIds[it.tense.code].add(id)
                }

                println("$id. $text")
            }
            println("\n\n\n\n")
            groupWithId.forEach { (sentence, id) ->
                var text = sentence.text
                sentence.answers.forEach {
                    text = text.replaceFirst("%s", it.forms.first())
                }

                if (text.last().isLetter()) {
                    text += '.'
                }

                println("$id. $text")
            }
            println("\n\n\n\n")


            group.clear()
        }
    }

    ObjectOutputStream(FileOutputStream("output/compiler/NewIds")).use {
        val array = oldIds.map { it.toIntArray() }.toTypedArray()
        it.writeObject(array)
    }

}

val Person.c
    get() = when (this) {
        Person.ME -> 1
        Person.YOU -> 2
        Person.IT -> 3
        else -> error("")
    }*/
