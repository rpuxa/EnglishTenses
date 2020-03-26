package ru.rpuxa.englishtenses.parser

import org.jsoup.Jsoup
import ru.rpuxa.englishtenses.model.IrregularVerb
import java.io.*

val irregularVerbs = IrregularVerb.values()
val allWords = mutableSetOf(
    "skied",
    "mows",
    "knits",
    "interrogates",
    "dripped",
    "dusts",
    "tidies",
    "cycled",
    "postpones",
    "preferring"
)
val popularWords = run {
    val popularWords = HashSet<String>()
    File("output/en.txt").bufferedReader().lines().forEach {
        val (first, second) = it.split(' ')
        val score = second.toInt()
        if (score > 50)
            allWords.add(first)
        if (score > 5000)
            popularWords.add(first)
    }
    popularWords
}


val continuousVerbs = mapOf(
    "writing" to "write",
    "lying" to "lie",
    "becoming" to "become"
)

val modalVerbs = setOf(
    "can", "could",
    "may", "might",
    "shall", "should",
    "will", "would",
    "was", "were",
    "must"
)

val pretexts = setOf(
    "off",
    "out",
    "in",
    "up",
    "down",
    "away",
    "over",
    "on",
    "through",
    "along",
    "across",
    "by"
)
/*

fun main() {
    val doc = Jsoup.connect("http://begin-english.ru/study/irregular-verbs/")
        .get()
    var id = 1
    val list = doc.select("table")
        .first { it.className() == "profile" }
        .child(0)
        .children()
        .mapIndexed { index, element ->
            if (index == 0) return@mapIndexed null
            if (element.childrenSize() != 4) return@mapIndexed null

            IrregularVerb(
                element.child(0).text().trim(),
                element.child(1).text().split('/').map { it.trim() },
                element.child(2).text().split('/').map { it.trim() }
            )
        }
    list.filterNotNull().forEach {
        println(
            "${it.first.toUpperCase()}(\"${it.first}\", listOf(${it.second.joinToString(", ") { '\"' + it + '\"' }}), listOf(${it.third.joinToString(
                ", "
            ) { '\"' + it + '\"' }})),"
        )
    }

    DataOutputStream(FileOutputStream("output/verbs.dat")).use { output ->
        val newList = list.filterNotNull()
        output.writeInt(newList.size)
        newList.forEach { verb ->
            output.writeUTF(verb.first)
            output.writeInt(verb.second.size)
            verb.second.forEach { output.writeUTF(it) }
            output.writeInt(verb.third.size)
            verb.third.forEach { output.writeUTF(it) }
        }
    }
}
*/
