package ru.rpuxa.englishtenses.parser.raw

import ru.rpuxa.englishtenses.model.Person
import java.io.Serializable

data class RawAnswer(
    var person: Person,
    var infinitive: String,
    var forms: List<String>
) : Serializable