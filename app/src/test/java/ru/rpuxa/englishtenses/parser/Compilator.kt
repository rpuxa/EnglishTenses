package ru.rpuxa.englishtenses.parser

import ru.rpuxa.englishtenses.model.Tense
import java.io.*

fun main() {
    val list = ObjectInputStream(FileInputStream("output/handledSentences")).use {
        it.readObject() as List<SentenceToCheck>
    }

    val tenseStreams = Tense.values()
        .map { DataOutputStream(FileOutputStream("output/sentences/tense${it.code}.index")) }
    DataOutputStream(FileOutputStream("output/sentences/sentences.data")).use { output ->
        list.forEach { sentence ->
            sentence.ans.forEach {
                tenseStreams[it.tense.code].writeInt(output.size())
            }
            output.writeUTF(sentence.text)
            output.writeByte(sentence.ans.size)
            sentence.ans.forEach {
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