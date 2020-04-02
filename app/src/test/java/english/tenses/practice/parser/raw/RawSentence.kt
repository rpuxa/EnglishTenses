package english.tenses.practice.parser.raw

import java.io.Serializable

data class RawSentence(
    val id: Int,
    var text: String,
    val answers: List<RawAnswer>
) : Serializable