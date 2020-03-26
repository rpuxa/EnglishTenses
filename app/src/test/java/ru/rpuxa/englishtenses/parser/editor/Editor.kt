package ru.rpuxa.englishtenses.parser.editor

import ru.rpuxa.englishtenses.model.Person
import ru.rpuxa.englishtenses.model.Tense
import ru.rpuxa.englishtenses.model.TenseHandler
import ru.rpuxa.englishtenses.parser.allWords
import ru.rpuxa.englishtenses.parser.handler.HANDLED_FILE_NAME
import ru.rpuxa.englishtenses.parser.handler.HandledSentence
import ru.rpuxa.englishtenses.parser.handler.words
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