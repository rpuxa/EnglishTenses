package ru.rpuxa.englishtenses.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class Sentence(
    val items: List<SentenceItem>
) {
    val answers get() = items.filterIsInstance<WordAnswer>()
}

sealed class SentenceItem

class Word(val text: String) : SentenceItem()

data class WordAnswer(
    val infinitive: String,
    val correctForms: List<String>,
    val tense: Tense,
    val irregular: IrregularVerb?,
    val wrongVariants: List<String>
) : SentenceItem() {
    val correctForm get() = correctForms.first()
}