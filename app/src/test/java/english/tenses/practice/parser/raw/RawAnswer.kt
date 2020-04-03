package english.tenses.practice.parser.raw

import english.tenses.practice.model.enums.Person
import java.io.Serializable

data class RawAnswer(
    var person: Person,
    var infinitive: String,
    var forms: List<String>
) : Serializable