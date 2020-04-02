package english.tenses.practice.parser.compiler

import english.tenses.practice.model.Languages
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import java.io.*
import java.security.MessageDigest
import kotlin.experimental.and

fun main() {
    val list = ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
        it.readObject() as List<HandledSentence>
    }

    val text = buildString {
        append("{\n")
        append("\"sentences\" : \"")
        list.forEach { sentence ->
            append(sentence.text)
            append("#")
            sentence.answers.joinToString("@") {
                append(it.simpleAnswer!!.infinitive)
                append("#")
                append(it.simpleAnswer!!.forms.first())
                append("#")
                append(it.tense.code.toString(16))
                append("#")
                append(it.verb)
                append("#")
                append(it.subject ?: "")
                append("#")
                append(it.person.code)
            }
            append("@")
        }
        append("\",\n")
        Languages.values().forEachIndexed { index, lang ->
            val name = "output/translates/${lang.code}"
            DataInputStream(FileInputStream(name)).use {
                append("\"${lang.code}\" : \"")
                val list = ArrayList<String>()
                try {
                    while (true) {
                        list += it.readUTF()
                    }
                } catch (e: EOFException) {
                }
                append(list.joinToString("#"))
                append('"')
                append(',')
                append('\n')
            }
        }
    }

    val json = buildString {
        val hash = MessageDigest.getInstance("SHA-256").digest(text.toByteArray())
        val hashStr = hash.joinToString("") {
            val first = it.toInt() and 0b1111
            val second = (it.toInt() shr 4) and 0b1111
            "${first.toString(16)}${second.toString(16)}"
        }
        append(text)
        append("\"hash\" : \"$hashStr\"\n")
        append("}")
    }

    File("output/compiler/sentences.json").printWriter().use {
        it.print(json)
    }
}