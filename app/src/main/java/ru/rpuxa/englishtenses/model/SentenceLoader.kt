package ru.rpuxa.englishtenses.model

import android.content.Context
import android.util.Log
import java.io.DataInputStream

class SentenceLoader(
    private val context: Context,
    private val sentencesHandler: SentenceStatistic
) {


    fun load(tenses: Set<Int>): Sentence {
        val sizes = Tense.values().map {
            val stream = context.assets.open(
                "$FOLDER/${TENSE_FILE.format(it.code)}"
            )
            val available = stream.available()
            stream.close()
            available / Int.SIZE_BYTES
        }
        val (tenseCode, id) = sentencesHandler.nextSentence(tenses, sizes)


        val shift = context.assets.open("$FOLDER/${TENSE_FILE.format(tenseCode)}").use {
            it.skip((Int.SIZE_BYTES * id).toLong())
            DataInputStream(it).readInt()
        }

       val sentence = context.assets.open("$FOLDER/$SENTENCES_FILE").use {
            it.skip(shift.toLong())
            val input = DataInputStream(it)
            UnhandledSentence(
                input.readUTF(),
                List(input.readByte().toInt()) {
                    UnhandledAnswer(
                        input.readUTF(),
                        List(input.readByte().toInt()) {
                            input.readUTF()
                        },
                        Tense[input.readByte().toInt()],
                        input.readUTF(),
                        input.readUTF(),
                        Person[input.readByte().toInt()]
                    )
                }
            )
        }

        return sentencesHandler.handle(sentence, tenses)
    }

    companion object {
        const val FOLDER = "sentences"
        const val TENSE_FILE = "tense%d.index"
        const val SENTENCES_FILE = "sentences.data"
        const val TAG = "SentenceLoaderDebug"
    }
}