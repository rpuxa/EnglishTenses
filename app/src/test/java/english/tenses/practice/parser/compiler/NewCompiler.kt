package english.tenses.practice.parser.compiler

import english.tenses.practice.model.enums.Language
import english.tenses.practice.parser.CACHE
import english.tenses.practice.parser.editor.sentences
import english.tenses.practice.parser.handler.HANDLED_FILE_NAME
import english.tenses.practice.parser.handler.HandledSentence
import java.io.*
import java.security.MessageDigest

fun main() {
    val list = ObjectInputStream(FileInputStream(HANDLED_FILE_NAME)).use {
        it.readObject() as List<HandledSentence>
    }

    val translates = ObjectInputStream(FileInputStream(CACHE)).use {
        it.readObject() as Map<Language, Map<String, String>>
    }

    val text = buildString {
        append("{\n")
        append("\"sentences\" : \"")
        list.forEach { sentence ->
            append(sentence.id)
            append("#")
            append(sentence.text)
            append("#")
            append(
                sentence.answers.joinToString("@") {
                    buildString {
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
                }
            )
            append("@#")
        }
        append("\",\n")
        (Language.values().toSet() - Language.ENGLISH).forEachIndexed { index, lang ->
            append("\"${lang.code}\" : \"")
            append(
                list.joinToString("") { sentence ->
                    var text = sentence.text
                    sentence.answers.forEach {
                        require("%s" in text)
                        text = text.replaceFirst("%s", it.simpleAnswer!!.forms.first())
                    }
                    text = text.first().toUpperCase() + text.substring(1)

                    val s = ((translates[lang] ?: error(lang))[text] ?: error(text)).shielding()
                    "${sentence.id}#$s#"
                }
            )
            append('"')
            append(',')
            append('\n')
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

fun String.shielding(): String =
    replace("\"", "\\\"")