package english.tenses.practice.parser

import english.tenses.practice.model.enums.Language
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import java.io.*
import kotlin.concurrent.thread

const val CACHE = "output/translates/cache"
const val API =
    "trnsl.1.1.20200404T155748Z.ce0ee1c534e4c24d.f79a357b4dc2240d7b2ac11831ca03f956b032dc"

var stop = false

fun main(): Unit = runBlocking<Unit> {
    val server = Server.create()

    val list = ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
        it.readObject() as List<HandledSentence>
    }

    thread {
        readLine()
        stop = true
    }

    val translates = read()
    try {


        val builder = StringBuilder()
        list.forEach { sentence ->
            var text = sentence.text
            sentence.answers.forEach {
                require("%s" in text)
                text = text.replaceFirst("%s", it.simpleAnswer!!.forms.first())
            }
            text = text.first().toUpperCase() + text.substring(1)

            builder.append(text).append("\n\n\n")

            if (stop) throw CancellationException()

            (Language.values().toSet() - Language.ENGLISH).forEach {
                if (translates[it]!![text] == null) {
                    val translate = server.getTranslate(text, "en-${it.code}", API).text!!
                    println(translate)
                    translates[it]!![text] = translate
                }
            }
        }
    } finally {
        ObjectOutputStream(FileOutputStream(CACHE)).use {
            it.writeObject(translates)
        }
    }
}

fun read(): HashMap<Language, HashMap<String, String>> {
    try {
        return ObjectInputStream(FileInputStream(CACHE)).use { it.readObject() as  HashMap<Language, HashMap<String, String>> }
    } catch (e: FileNotFoundException) {
        return HashMap<Language, HashMap<String, String>>().apply {
            Language.values().forEach {
                this[it] = HashMap()
            }
        }
    }
}