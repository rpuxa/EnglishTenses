package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.*
import ru.rpuxa.englishtenses.State
import ru.rpuxa.englishtenses.event
import ru.rpuxa.englishtenses.model.*
import ru.rpuxa.englishtenses.update
import ru.rpuxa.englishtenses.view.fragments.ExerciseFragment
import javax.inject.Inject

class ExerciseViewModel @Inject constructor(
    private val user: User,
    private val loader: SentenceLoader
) : ViewModel() {


    private var tipsEnabled = false
    private val spacesStates = MutableLiveData<List<Int?>>()
    private val answerStates = MutableLiveData<MutableList<AnswerState>>()
    private val _tipMode = State(TIP_MODE_OFF)
    private val startTime = System.currentTimeMillis()

    lateinit var sentence: Sentence
    val tipMode = _tipMode.liveData
    var shuffledAnswers = emptyList<String>()
        private set
    var rightAnswers = emptyList<Int>()
        private set
    var result = State<ExerciseResult?>(null)
    val showTipButton: LiveData<Boolean> = spacesStates.map { tipsEnabled && it.any { it == null } }

    val answerListener = object : ExerciseFragment.AnswersMoveListener {
        override fun onMoveToSpace(answerId: Int, spaceId: Int): Boolean {

            spacesStates.value!![spaceId]?.let {
                if (answerStates.value!![it].right) {
                    return false
                }
            }

            if (_tipMode.value != TIP_MODE_OFF && _tipMode.value.spaceIndex == spaceId) {
                return if (rightAnswers[spaceId] == answerId) {
                    answerStates.update { this[answerId] = Result(correct = true, block = true) }
                    _tipMode.value = TIP_MODE_OFF
                    if (rightAnswers.all { answerStates.value!![it].right }) {
                        setResult(true)
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
        sentence = loader.load(user.sentences, set)
        shuffledAnswers =
            (sentence.answers.map { it.forms.first() } + sentence.wrongVariants).shuffled()
        rightAnswers = sentence.answers.map { shuffledAnswers.indexOf(it.forms.first()) }

        answerStates.value = MutableList(shuffledAnswers.size) {
            None()
        }

        spacesStates.value = List(sentence.answers.size) { null }
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

    fun check(spaces: List<Int?>) {
        var allRight = true
        answerStates.update {
            spaces.forEachIndexed { spaceId, answerId ->
                if (answerId == null) {
                    allRight = false
                } else {
                    this[answerId] = Result(
                        if (rightAnswers[spaceId] == answerId) {
                            true
                        } else {
                            allRight = false
                            false
                        }
                    )
                }
            }
            forEach { it.block = true }
        }
        setResult(allRight)
    }



    fun setAllCorrect() {
        answerStates.update {
            repeat(size) {
                this[it] = if (it in rightAnswers) Result(true, block = true) else None(true)
            }
        }
    }

    fun updateSpaces(answers: List<Int?>) {
        spacesStates.value = answers
    }


    private fun setResult(correct: Boolean) {
        if (result.value == null) {
            result.value = ExerciseResult(correct, System.currentTimeMillis() - startTime)
        }
    }

    class TipMode(
        val tense: Tense,
        val spaceIndex: Int
    )


    companion object {
        val TIP_MODE_OFF = TipMode(Tense.PRESENT_SIMPLE, -1)
    }
}