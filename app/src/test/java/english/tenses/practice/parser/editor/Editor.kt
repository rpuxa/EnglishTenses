package english.tenses.practice.parser.editor

import english.tenses.practice.model.Person
import english.tenses.practice.model.Tense
import english.tenses.practice.model.TenseHandler
import english.tenses.practice.parser.allWords
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import english.tenses.practice.parser.handler.words
import java.io.*

fun main() {
    try {
        read()
        checkAllTenses()
    } finally {
        write()
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