package ru.rpuxa.englishtenses.model

data class WordAnswer(
    val infinitive: String,
    val forms: List<String>,
    val tense: Tense
)