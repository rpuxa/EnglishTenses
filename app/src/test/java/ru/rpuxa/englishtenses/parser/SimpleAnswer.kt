package ru.rpuxa.englishtenses.parser

import java.io.Serializable

data class SimpleAnswer(
    val infinitive: String,
    val forms: List<String>
) : Serializable