package ru.rpuxa.englishtenses.parser

import org.jsoup.Jsoup
import ru.rpuxa.englishtenses.model.IrregularVerb
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

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

            println(element.text())
            IrregularVerb(
                id++,
                element.child(0).text().trim(),
                element.child(1).text().split('/').map { it.trim() },
                element.child(2).text().split('/').map { it.trim() }
            )
        }

    DataOutputStream(FileOutputStream("output/verbs.dat")).use { output ->
        val newList = list.filterNotNull()
        output.writeInt(newList.size)
        newList.forEach { verb: IrregularVerb ->
            output.writeUTF(verb.first)
            output.writeInt(verb.second.size)
            verb.second.forEach { output.writeUTF(it) }
            output.writeInt(verb.third.size)
            verb.third.forEach { output.writeUTF(it) }
        }
    }
}

fun loadIrregularVerbs() =
    DataInputStream(FileInputStream("output/verbs.dat")).use {input ->
        var id = 1
        val size = input.readInt()
        List(size) {
            IrregularVerb(
                id++,
                input.readUTF(),
                List(input.readInt()) { input.readUTF() },
                List(input.readInt()) { input.readUTF() }
            )
        }
    }
