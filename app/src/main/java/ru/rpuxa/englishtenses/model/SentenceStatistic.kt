package ru.rpuxa.englishtenses.model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.rpuxa.englishtenses.model.db.CorrectnessStatistic
import ru.rpuxa.englishtenses.model.db.CorrectnessStatisticDao
import ru.rpuxa.englishtenses.model.db.LearnedSentenceEntity
import ru.rpuxa.englishtenses.model.db.LearnedSentencesDao
import kotlin.random.Random

class SentenceStatistic(
    private val learnedSentencesDao: LearnedSentencesDao,
    private val correctnessStatisticDao: CorrectnessStatisticDao
) {
    private var _learned: List<MutableSet<Int>>? = null

    val learned: List<MutableSet<Int>>
        get() = _learned ?: runBlocking { load() }

    fun nextSentence(tenses: Set<Int>, sizes: List<Int>): SentencePointer {
        val tensesArray = tenses.toIntArray()
        val sum = tensesArray.sumBy { sizes[it] }
        val chances = tensesArray.map { sizes[it].toDouble() / sum }
        val randomDouble = Random.nextDouble()
        var d = 0.0
        val i = chances.indices.first {
            d += chances[it]
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
        val items = text.map {
            if (it == "%s") {
                val answer = sentence.answers[index++]
                if (answer.tense.code in tenses) {
                    val wrongAnswers = ArrayList<String>()
                    val unusedTenses = tenses.toMutableSet()
                    unusedTenses -= answer.tense.code
                    for (i in 0 until WRONG_ANSWER_AMOUNT) {
                        if (unusedTenses.isEmpty()) break
                        val random = unusedTenses.random()
                        unusedTenses -= random
                        wrongAnswers += answer.createWrongAnswer(Tense[random])
                    }
                    answer.toWordAnswer(wrongAnswers)
                } else {
                    Word(answer.forms.first())
                }
            } else {
                Word(it)
            }
        }
        return Sentence(items)
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

    fun addResult(result: List<CorrectnessStatistic>) {
        GlobalScope.launch {
            result.forEach {
                val statistic = correctnessStatisticDao.get(it.tenseCode) ?: CorrectnessStatistic(
                    it.tenseCode,
                    0,
                    0
                )
                statistic += it
                correctnessStatisticDao.update(statistic)
            }
        }
    }


    data class SentencePointer(
        val tenseCode: Int,
        val id: Int
    )

    companion object {
        const val WRONG_ANSWER_AMOUNT = 3
    }
}