package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.*
import ru.rpuxa.englishtenses.SingleLiveEvent
import ru.rpuxa.englishtenses.State
import ru.rpuxa.englishtenses.model.*
import ru.rpuxa.englishtenses.model.db.CorrectnessStatistic
import ru.rpuxa.englishtenses.update
import ru.rpuxa.englishtenses.view.fragments.ExerciseFragment
import javax.inject.Inject

class ExerciseViewModel @Inject constructor(
    private val loader: SentenceLoader
) : ViewModel() {


    private var tipsEnabled = false
    private val spacesStates = MutableLiveData<List<ExerciseFragment.SpaceState>>()
    private val answerStates = MutableLiveData<MutableList<AnswerState>>()
    private val _tipMode = State(TIP_MODE_OFF)
    private val startTime = System.currentTimeMillis()

    lateinit var sentence: Sentence
    val tipMode = _tipMode.liveData
    var shuffledAnswers = emptyList<String>()
        private set
    var rightAnswers = emptyList<String>()
        private set
    var result = State<ExerciseResult?>(null)
    val showTipButton: LiveData<Boolean> = spacesStates.map { tipsEnabled && it.any { it.empty } }
    val showIrregularVerbTable = SingleLiveEvent<String>()

    val answerListener = object : ExerciseFragment.AnswersMoveListener {
        override fun onMoveToSpace(answerId: Int, spaceId: Int): Boolean {

            spacesStates.value!![spaceId].let {
                if (!it.empty && answerStates.value!![it.answerId].right) {
                    return false
                }
            }

            if (_tipMode.value != TIP_MODE_OFF && _tipMode.value.spaceIndex == spaceId) {
                return if (rightAnswers[spaceId] == shuffledAnswers[answerId]) {
                    answerStates.update { this[answerId] = Result(correct = true, block = true) }
                    _tipMode.value = TIP_MODE_OFF
                    if (spacesStates.value!!.all { it.spaceId == spaceId || !it.empty && answerStates.value!![it.answerId].right }) {
                        setResult(sentence.answers, emptyList())
                    }
                    true
                } else {
                    answerStates.update { this[answerId] = WrongSignal }
                    false
                }
            }
            return true
        }

        override fun onTouch(id: Int): Boolean {
            val state = answerStates.value!![id]
            if (state.block) return false
            if (state.wrong) {
                answerStates.update {
                    this[id] = None()
                }
            }
            return true
        }
    }

    fun load(set: Set<Int>, tipsEnabled: Boolean) {
        this.tipsEnabled = tipsEnabled
        sentence = testSentence ?: loader.load(set)
        val answers = sentence.answers
        shuffledAnswers = (answers.map { it.correctForm } + sentence.wrongAnswers).shuffled()
        rightAnswers = answers.map { it.correctForm }

        answerStates.value = MutableList(shuffledAnswers.size) {
            None()
        }

        spacesStates.value = List(answers.size) { ExerciseFragment.SpaceState(it) }
    }

    fun getAnswerState(id: Int) = answerStates.map { it[id] }

    fun tipModeOn(index: Int) {
        if (tipsEnabled) {
            _tipMode.value = TipMode(sentence.answers[index].tense, index)
        }
    }

    fun resetState(index: Int) {
        answerStates.value!![index] = None()
    }

    fun check(spaces: List<ExerciseFragment.SpaceState>) {
        val correct = ArrayList<WordAnswer>()
        val incorrect = ArrayList<WordAnswer>()
        val answers = sentence.answers
        answerStates.update {
            spaces.forEach {
                val answer = answers[it.spaceId]
                if (it.empty) {
                    incorrect += answer
                } else {
                    this[it.answerId] = Result(
                        if (rightAnswers[it.spaceId] == it.text) {
                            correct += answer
                            true
                        } else {
                            incorrect += answer
                            false
                        }
                    )
                }
            }
            forEach { it.block = true }
        }
        setResult(correct, incorrect)
    }


    fun setAllCorrect(): List<Int> {
        val used = ArrayList<Int>()

        answerStates.update {
            repeat(size) {
                val answer = shuffledAnswers[it]
                var index = -1
                for (i in rightAnswers.indices) {
                    if (i !in used && rightAnswers[i] == answer) {
                        used += i
                        index = i
                        break
                    }
                }
                if (index == -1) {
                    this[it] = None(true)
                } else {
                    this[it] = Result(true, block = true)
                }
            }
        }

        return used
    }

    fun updateSpaces(answers: List<ExerciseFragment.SpaceState>) {
        spacesStates.value = answers
    }


    private fun setResult(correct: List<WordAnswer>, incorrect: List<WordAnswer>) {
        if (result.value == null) {
            val correctness = HashMap<Tense, CorrectnessStatistic>()
            fun add(list: List<WordAnswer>, correct: Boolean) {
                list.forEach {
                    val statistic =
                        correctness[it.tense] ?: CorrectnessStatistic(it.tense.code, 0, 0)
                    statistic.addResult(correct)
                    correctness[it.tense] = statistic
                }
            }
            add(correct, true)
            add(incorrect, false)
            result.value = ExerciseResult(
                correctness.values.toList(),
                incorrect.isEmpty(),
                System.currentTimeMillis() - startTime
            )
        }
    }

    fun irregularVerbsClick() {
        val query = sentence.answers
            .filter { it.irregular != null }
            .joinToString(", ") {
                it.irregular!!.first
            }

        showIrregularVerbTable.value = query
    }

    class TipMode(
        val tense: Tense,
        val spaceIndex: Int
    )


    companion object {
        val TIP_MODE_OFF = TipMode(Tense.PRESENT_SIMPLE, -1)
        private val testSentence: Sentence? =null/*Sentence(
            listOf(
                Word("Hi"),
                WordAnswer("read", listOf("read"), Tense.PAST_SIMPLE, null),
                WordAnswer("read", listOf("read"), Tense.PAST_SIMPLE, null)
            ),
            listOf(
                "readasdfasdfasdf"
            )
        )*/
    }
}