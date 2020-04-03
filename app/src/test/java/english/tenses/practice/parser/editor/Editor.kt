package english.tenses.practice.parser.editor

import english.tenses.practice.model.enums.Person
import english.tenses.practice.model.enums.Tense
import english.tenses.practice.model.logic.TenseHandler
import english.tenses.practice.parser.allWords
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import english.tenses.practice.parser.handler.words
import java.io.*

fun main() {
    read()
    checkAllTenses()
    checkDoubleVerbs()
}

fun checkDoubleVerbs() {
    val regex = Regex("\\S+")

    sentences.forEach {
        it.answers.forEach { answer ->
            Tense.values().forEach { tense ->
                val wrongAnswer = TenseHandler.createWrongAnswer(
                    tense,
                    answer.verb,
                    answer.subject ?: "",
                    answer.person
                )
                val sequence = regex.findAll(wrongAnswer).map { it.groups[0]!!.value }
                if (sequence.count { it == "have" } > 1 || sequence.count { it == "been" } > 1 || sequence.count { it == "will" } > 1) {
                    println(wrongAnswer)
                }
            }
        }
    }
}

fun checkAllTenses() {
    val unknownWords = HashSet<String>()
    sentences.forEach {
        it.answers.forEach { answer ->
            Tense.values().forEach { tense ->
                arrayOf("", "subject").forEach { subject ->
                    arrayOf(Person.IT, Person.YOU, Person.ME).forEach { person ->


                        val a = TenseHandler.createWrongAnswer(
                            tense,
                            answer.verb,
                            subject,
                            person
                        )

                        a.words().forEach {
                            if (it !in allWords) {
                                unknownWords += it + "  " + answer.verb
                            }
                        }


                    }
                }
            }
        }
    }

    unknownWords.forEach {
        System.err.println("Unknown word: $it")
    }
}


lateinit var sentences: MutableList<HandledSentence>

fun write() {
    ObjectOutputStream(FileOutputStream(HANDLED_FILE_NAME)).use {
        it.writeObject(sentences)
    }
}

fun read() {
    sentences = try {
        ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
            it.readObject() as MutableList<HandledSentence>
        }
    } catch (e: IOException) {
        ArrayList()
    }
}