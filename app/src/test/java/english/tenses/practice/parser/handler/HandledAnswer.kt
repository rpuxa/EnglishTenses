package english.tenses.practice.parser.handler

import english.tenses.practice.model.Person
import english.tenses.practice.model.Tense
import english.tenses.practice.parser.raw.RawAnswer
import java.io.Serializable

data class HandledAnswer(
    val tense: Tense,
    var verb: String,
    val subject: String?,
    var person: Person,
    var needsCheck: Boolean,
    var text: String? = null
) : Serializable {
    var simpleAnswer: RawAnswer? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HandledAnswer

        if (tense != other.tense) return false
        if (verb != other.verb) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tense.hashCode()
        result = 31 * result + verb.hashCode()
        result = 31 * result + (text?.hashCode() ?: 0)
        return result
    }


}