package ru.rpuxa.englishtenses.parser.raw

import ru.rpuxa.englishtenses.parser.raw.RawAnswer
import java.io.Serializable

data class RawSentence(
    var text: String,
    val answers: List<RawAnswer>
) : Serializable