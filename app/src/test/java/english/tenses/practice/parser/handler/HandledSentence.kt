package english.tenses.practice.parser.handler

import java.io.Serializable

data class HandledSentence(
    val id: Int,
    val text: String,
    val answers: List<HandledAnswer>
) : Serializable