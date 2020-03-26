package ru.rpuxa.englishtenses.parser.raw

import java.io.FileOutputStream
import java.io.ObjectOutputStream

val RAW_FILE_NAMES = arrayOf(
    "output/raw/english grammar site"
)

fun main() {
    ObjectOutputStream(FileOutputStream(RAW_FILE_NAMES.last())).use {
        val loadAll = TextSource.parse()
        println("Count: ${loadAll.size}")
        loadAll.forEach {
            it.text = it.text.clearNewLines()
            it.answers.forEach {
                it.infinitive = it.infinitive.toLowerCase().clearNewLines()
                it.forms = it.forms.map { it.toLowerCase().clearNewLines() }
            }
        }
        it.writeObject(loadAll)
    }
}

fun String.clearNewLines() = replace('\n', ' ').replace('\r', ' ')