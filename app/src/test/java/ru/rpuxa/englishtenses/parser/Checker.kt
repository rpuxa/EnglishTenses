package ru.rpuxa.englishtenses.parser

import ru.rpuxa.englishtenses.model.Person
import ru.rpuxa.englishtenses.model.Sentence
import java.io.*
import java.util.concurrent.CancellationException

lateinit var sentences: MutableSet<SentenceToCheck>

fun main() {
    read()
    try {
        handleUnhandled()
    } finally {
        write()
    }
}

fun handleUnhandled() {
    val it = File("output/examples/english grammar")
    ObjectInputStream(FileInputStream(it)).use {
        (it.readObject() as List<SentenceToCheck>).forEach { sentence ->
            if (sentences.find { it.text == sentence.text } == null) {
                sentence.ans.forEachIndexed { index, ans ->
                    if (ans.needsCheck) {
                        println("\n\n\n\n\n\n\n")
                        println(sentence.text)
                        println("Index: $index")
                        println("${ans.subject}    ${ans.person}")
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
                            ans.person = result
                        }
                    }

                    sentences.add(sentence)
                }
            }
        }
    }
}

const val FILE_NAME ="output/handledSentences"
fun write() {
    ObjectOutputStream(FileOutputStream(FILE_NAME)).use {
        it.writeObject(sentences)
    }
}

fun read() {
    sentences = try {
        ObjectInputStream(FileInputStream(FILE_NAME)).use {
            it.readObject() as MutableSet<SentenceToCheck>
        }
    } catch (e: IOException) {
        HashSet()
    }
}