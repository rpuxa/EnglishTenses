package ru.rpuxa.englishtenses.parser.handler

import java.io.Serializable

data class HandledSentence(
    val text: String,
    val answers: List<HandledAnswer>
) : Serializable