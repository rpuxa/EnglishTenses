package ru.rpuxa.englishtenses.parser

import ru.rpuxa.englishtenses.model.Sentence

interface SentenceSource {

    fun loadSentences(): List<SimpleSentence>
}