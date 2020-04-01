package english.tenses.practice.model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import english.tenses.practice.model.db.CorrectnessStatistic
import english.tenses.practice.model.db.CorrectnessStatisticDao
import english.tenses.practice.model.db.LearnedSentenceEntity
import english.tenses.practice.model.db.LearnedSentencesDao
import english.tenses.practice.random
import kotlin.random.Random

class SentenceStatistic(
    private val learnedSentencesDao: LearnedSentencesDao,
    private val correctnessStatisticDao: CorrectnessStatisticDao
) {
    private var _learned: List<MutableSet<Int>>? = null

    val learned: List<MutableSet<Int>>
        get() = _learned ?: runBlocking { load() }

    fun nextSentence(tenses: Set<Int>, sizes: List<Int>): SentencePointer {
        val tense = random(tenses.map { it to sizes[it].toDouble() })
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

    fun handle(sentence: UnhandledSentence, tenses: Set<Int>, sizes: List<Int>): Sentence {
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
                        val random = random(unusedTenses.map { it to sizes[it].toDouble() })
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
        repeat(Tense.values().count()) {
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