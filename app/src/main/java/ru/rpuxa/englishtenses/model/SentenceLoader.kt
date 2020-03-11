package ru.rpuxa.englishtenses.model

import android.content.Context

class SentenceLoader(private val context: Context) {



    fun load(sentences: LearnedSentences, tenses: Set<Int>): Sentence {


        return Sentence(
            1,
            "What %s now?",
            listOf(
                WordAnswer(
                    "to do",
                    listOf("are you doing"),
                    Tense.PRESENT_CONTINUOUS
                )
            ),
            listOf(
                "do you do",
                "has been doing",
                "has you done"
            )
        )
    }
}