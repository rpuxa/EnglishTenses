package english.tenses.practice.parser

import com.google.gson.annotations.SerializedName

class Translate {

    @SerializedName("text")
    private var _text: Array<String>? = null

    val text: String? by lazy {
        val text = _text ?: return@lazy null
        val result = StringBuilder()
        for ((i, line) in text.withIndex()) {
            result.append(line)
            if (i != text.lastIndex)
                result.append('\n')
        }

        result.toString()
    }
}