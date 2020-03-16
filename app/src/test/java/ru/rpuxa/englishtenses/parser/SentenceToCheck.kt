package ru.rpuxa.englishtenses.parser

import java.io.Serializable

data class SentenceToCheck(
    val text: String,
    val ans: List<Ans>
) : Serializable