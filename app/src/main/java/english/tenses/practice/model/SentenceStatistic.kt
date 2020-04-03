package english.tenses.practice.model

import english.tenses.practice.model.db.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import english.tenses.practice.random
import english.tenses.practice.toMask
import kotlin.random.Random

class SentenceStatistic(
    private val learnedSentencesDao: LearnedSentencesDao2,
    private val correctnessStatisticDao: CorrectnessStatisticDao,
    private val sentencesDao: SentencesDao
) {
    private var _learned: HashSet<LearnedSentence2>? = null
    private val learned: HashSet<LearnedSentence2>
        get() {
            if (_learned == null) {
                load()
            }
            return _learned!!
        }

    suspend fun nextSentence(tenses: Set<Int>): Sentence {
        val sizes = sentencesDao.sizes()
        val tense = random(tenses.map { it to sizes[it].toDouble() })
        val clear = learned.size >= sizes[tense]
        val tenseMask = toMask(tense)
        if (clear) {
            learned.removeAll { it.tenseMask and tenseMask != 0 }
        }
        val learnedTense = HashSet<Int>()
        learned.forEach {
            if (it.tenseMask and tenseMask != 0) {
                learnedTense += it.id
            }
        }
        val randomIndex = Random.nextInt(sizes[tense] - learnedTense.size)
        val all = sentencesDao.getAllByTense(tenseMask)

        var index = 0
        val result = all.first { it !in learnedTense && randomIndex == index++ }
        val sentence = sentencesDao.getById(result)
        val learnedSentence2 = LearnedSentence2(
            sentence.sentence.id,
            sentence.sentence.tenseMask
        )
        GlobalScope.launch {
            if (clear) {
                learnedSentencesDao.clear(tense)
            }
            learnedSentencesDao.insert(learnedSentence2)
        }
        learned.add(learnedSentence2)
        return handle(sentence, tenses, sizes)
    }

    private fun handle(
        sentence: SentenceWithAnswers,
        tenses: Set<Int>,
        sizes: List<Int>
    ): Sentence {
        val text = sentence.sentence.text.split(' ').filter { it.isNotBlank() }.map { it.trim() }
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
                        wrongAnswers += TenseHandler.createWrongAnswer(
                            Tense[random],
                            answer.verb,
                            answer.subject,
                            answer.person
                        )
                    }
                    WordAnswer(
                        answer.infinitive,
                        listOf(answer.correct),
                        answer.tense,
                        IrregularVerb.byFirst(answer.verb),
                        wrongAnswers
                    )
                } else {
                    Word(answer.correct)
                }
            } else {
                Word(it)
            }
        }
        return Sentence(items)
    }


    fun load() {
        _learned = runBlocking {
            learnedSentencesDao.getAll().toHashSet()
        }
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

    companion object {
        const val WRONG_ANSWER_AMOUNT = 3
    }
}