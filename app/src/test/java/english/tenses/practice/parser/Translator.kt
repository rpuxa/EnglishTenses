package english.tenses.practice.parser

import english.tenses.practice.model.enums.Language
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream

fun main() {
    val list = ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
        it.readObject() as List<HandledSentence>
    }

    val translates = Language.values()
        .map { it to DataOutputStream(FileOutputStream("output/translates/${it.code}")) }.toMap()


    try {
        list.forEach {sentence ->
            var text = sentence.text
            sentence.answers.forEach {
                require("%s" in text)
                text = text.replaceFirst("%s", it.simpleAnswer!!.infinitive)
            }
            text = text.first().toUpperCase() + text.substring(1)

            Language.values().forEach {
                val dataOutputStream = translates[it]!!
                dataOutputStream.writeInt(sentence.id)
                dataOutputStream.writeUTF(text) // make translate
            }
        }
    } finally {
        translates.values.forEach { it.close() }
    }
}