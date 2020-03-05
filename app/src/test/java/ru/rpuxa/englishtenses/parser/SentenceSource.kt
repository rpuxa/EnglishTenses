package ru.rpuxa.englishtenses.parser

import ru.rpuxa.englishtenses.Sentence

interface SentenceSource {

    fun loadSentences(): List<Sentence>
}