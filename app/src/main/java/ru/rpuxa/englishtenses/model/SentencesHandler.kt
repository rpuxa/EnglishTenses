package ru.rpuxa.englishtenses.model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.rpuxa.englishtenses.model.db.LearnedSentenceEntity
import ru.rpuxa.englishtenses.model.db.LearnedSentencesDao
import kotlin.random.Random

class SentencesHandler(
    private val learnedSentencesDao: LearnedSentencesDao
) {
    private val learnedSession = IntArray(Tense.values().size)
    private var _learned: List<MutableSet<Int>>? = null

    val learned: List<MutableSet<Int>>
        get() = _learned ?: runBlocking { load() }

    fun nextSentence(tenses: Set<Int>, sizes: List<Int>): SentencePointer {
        val tensesArray = tenses.toIntArray()
        val max = tensesArray.maxBy { learnedSession[it] }!! + 25
        val chances = tensesArray.map { (max - learnedSession[it]).coerceAtLeast(0) }
        val sum = chances.sum()
        val randomDouble = Random.nextDouble()
        var d = 0.0
        val i = chances.indices.first {
            d += chances[it].toDouble() / sum
            randomDouble < d
        }
        val tense = tensesArray[i]
        val learnedTense = learned[tense]
        val size = sizes[tense]
        val clear = learnedTense.size >= size
        if (clear) {
            learnedTense.clear()
        }
        require(size > 0)
        val randomIndex = Random.nextInt(size - learnedTense.size)
        var index = 0
        var result = -1
        for (id in 0 until size) {
            if (id !in learnedTense) {
                if (index == randomIndex) {
                    result = id
                    break
                } else {
                    index++
                }
            }
        }
        require(result != -1)
        learnedSession[tense]++
        learned[tense] += result
        GlobalScope.launch {
            if (clear) {
                learnedSentencesDao.clear(tense)
            }
            learnedSentencesDao.insert(
                LearnedSentenceEntity(
                    result, tense
                )
            )
        }
        return SentencePointer(tense, result)
    }

    fun handle(sentence: UnhandledSentence, tenses: Set<Int>): Sentence {
        val text = sentence.text.split(' ').filter { it.isNotBlank() }.map { it.trim() }
        var index = 0
        val wrongAnswers = ArrayList<String>()
        val items = text.map {
            if (it == "%s") {
                val answer = sentence.answers[index++]
                if (answer.tense.code in tenses) {
                    val unusedTenses = tenses.toMutableSet()
                    unusedTenses -= answer.tense.code
                    for (i in 0 until 3) {
                        if (unusedTenses.isEmpty()) break
                        val random = unusedTenses.random()
                        unusedTenses -= random
                        wrongAnswers += answer.createWrongAnswer(Tense[random])
                    }
                    answer.toWordAnswer()
                } else {
                    Word(answer.forms.first())
                }
            } else {
                Word(it)
            }
        }
        return Sentence(items, wrongAnswers)
    }

    fun resetSession() = learnedSession.indices.forEach {
        learnedSession[it] = 0
    }

    suspend fun load(): List<MutableSet<Int>> {
        val list = ArrayList<MutableSet<Int>>()
        Tense.values().forEach {
            list.add(HashSet())
        }
        learnedSentencesDao.getAll().forEach {
            list[it.tenseCode].add(it.id)
        }
        _learned = list
        return list
    }


    data class SentencePointer(
        val tenseCode: Int,
        val id: Int
    )

    companion object {
        const val WRONG_ANSWER_AMOUNT = 3
    }
}