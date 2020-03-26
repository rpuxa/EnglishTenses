package ru.rpuxa.englishtenses.parser.compiler

import ru.rpuxa.englishtenses.model.Tense
import ru.rpuxa.englishtenses.parser.handler.HANDLED_FILE_NAME
import ru.rpuxa.englishtenses.parser.handler.HandledSentence
import java.io.*

fun main() {
    val list = ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
        it.readObject() as List<HandledSentence>
    }

    val tenseStreams = Tense.values()
        .map { DataOutputStream(FileOutputStream("output/compiler/tense${it.code}.index")) }
    DataOutputStream(FileOutputStream("output/compiler/sentences.data")).use { output ->
        list.forEach { sentence ->
            sentence.answers.forEach {
                tenseStreams[it.tense.code].writeInt(output.size())
            }
            output.writeUTF(sentence.text)
            output.writeByte(sentence.answers.size)
            sentence.answers.forEach {
                output.writeUTF(it.simpleAnswer!!.infinitive)
                output.writeByte(it.simpleAnswer!!.forms.size)
                it.simpleAnswer!!.forms.forEach {
                    output.writeUTF(it)
                }

                output.writeByte(it.tense.code)
                output.writeUTF(it.verb)
                output.writeUTF(it.subject ?: "")
                output.writeByte(it.person.code)
            }
        }
    }
}