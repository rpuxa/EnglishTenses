package ru.rpuxa.englishtenses.model

data class Sentence(
    val id: Int,
    val text: String,
    val answers: List<WordAnswer>,
    val wrongVariants: List<String>
)

