package ru.rpuxa.englishtenses.parser.raw

import java.io.Serializable

data class RawAnswer(
    var infinitive: String,
    var forms: List<String>
) : Serializable