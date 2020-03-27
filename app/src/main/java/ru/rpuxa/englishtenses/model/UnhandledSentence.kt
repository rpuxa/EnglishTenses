package ru.rpuxa.englishtenses.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class UnhandledSentence(
    val text: String,
    val answers: List<UnhandledAnswer>
)


class UnhandledAnswer(
    val infinitive: String,
    val forms: List<String>,
    val tense: Tense,
    val verb: String,
    val subject: String,
    val person: Person
) {

    fun toWordAnswer(wrongAnswers: List<String>) = WordAnswer(
        infinitive, forms, tense,
        IrregularVerb.byFirst(verb),
        wrongAnswers
    )

    fun createWrongAnswer(tense: Tense): String {
        require(this.tense != tense)
        return TenseHandler.createWrongAnswer(tense, verb, subject, person)
    }


}