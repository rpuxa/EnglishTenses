package ru.rpuxa.englishtenses.model

class LearnedSentences(
     string: String
) {
    val set: MutableSet<Int> =  HashSet()
    private val builder = StringBuilder(string)

    init {
        var n = 0
        string.forEach {
            if (it == ',') {
                set += n
                n = 0
            } else {
                n = 10 * n + it.toIntRadix36()
            }
        }
    }

    fun add(id: Int) {
        "".toInt()
        builder.append(id.toString(36)).append(',')
        set.add(id)
    }

    private fun Char.toIntRadix36(): Int {
        return when(this) {
           in '0'..'1' -> this - '0'
            in 'a'..'z' -> this - 'a' + 10
            else -> error("Wrong symbol")
        }
    }

    override fun toString(): String {
        return builder.toString()
    }
}