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
    private var correct = true
    private val startTime = System.currentTimeMillis()

    lateinit var sentence: Sentence
    val tipMode = _tipMode.liveData
    var shuffledAnswers = emptyList<String>()
        private set
    var rightAnswers = emptyList<Int>()
        private set
    lateinit var result: ExerciseResult
        private set
    val showWrongMenu = event()
    val showTipButton: LiveData<Boolean> = spacesStates.map { tipsEnabled && it.any { it == null } }
    val allCorrect: LiveData<Boolean> = answerStates.map { state ->
        if (!correct) return@map false
        val result = rightAnswers.all { state[it] == AnswerState.RIGHT }
        if (result) {
            setResult()
        }
        result
    }

    val answerListener = object : ExerciseFragment.AnswersMoveListener {
        override fun onMoveToSpace(answerId: Int, spaceId: Int): Boolean {
            spacesStates.value!![spaceId]?.let {
                if (answerStates.value!![it] == AnswerState.RIGHT) {
                    return false
                }
            }

            if (_tipMode.value != TIP_MODE_OFF && _tipMode.value.spaceIndex == spaceId) {
                return if (rightAnswers[spaceId] == answerId) {
                    answerStates.update { this[answerId] = AnswerState.RIGHT }
                    _tipMode.value = TIP_MODE_OFF
                    true
                } else {
                    answerStates.update { this[answerId] = AnswerState.WRONG_SIGNAL }
                    false
                }
            }
            return true
        }

        override fun onTouch(id: Int): Boolean {
            val state = answerStates.value!![id]
            if (state == AnswerState.WRONG) {
                answerStates.update {
                    this[id] = AnswerState.NONE
                }
            }
            return state != AnswerState.RIGHT
        }
    }

    fun load(set: Set<Int>, tipsEnabled: Boolean) {
        this.tipsEnabled = tipsEnabled
        sentence = loader.load(user.sentences, set)
        shuffledAnswers =
            (sentence.answers.map { it.forms.first() } + sentence.wrongVariants).shuffled()
        rightAnswers = sentence.answers.map { shuffledAnswers.indexOf(it.forms.first()) }

        answerStates.value = MutableList(shuffledAnswers.size) {
            AnswerState.NONE
        }

        spacesStates.value = List(sentence.answers.size) { null }
    }

    fun getAnswerState(id: Int) = answerStates.map { it[id] }

    fun tipModeOn(index: Int) {
        if (tipsEnabled) {
            _tipMode.value = TipMode(sentence.answers[index].tense, index)
            correct = false
        }
    }

    fun resetState(index: Int) {
        answerStates.value!![index] = AnswerState.NONE
    }

    fun check(spaces: List<Int?>) {
        var allRight = true
        answerStates.update {
            spaces.forEachIndexed { spaceId, answerId ->
                if (answerId != null) {
                    this[answerId] = if (rightAnswers[spaceId] == answerId) {
                        AnswerState.RIGHT
                    } else {
                        allRight = false
                        AnswerState.WRONG
                    }
                }
            }
        }
        if (!allRight) {
            correct = false
            if (!tipsEnabled) {
                setResult()
                showWrongMenu.call()
            }
        }
    }

    fun setAllCorrect() {
        answerStates.update {
            repeat(size) {
                this[it] = if (it in rightAnswers) AnswerState.RIGHT else AnswerState.NONE
            }
        }
    }

    fun updateSpaces(answers: List<Int?>) {
        spacesStates.value = answers
    }


    private fun setResult() {
        if (!::result.isInitialized) {
            result = ExerciseResult(correct, System.currentTimeMillis() - startTime)
        }
    }

    class TipMode(
        val tense: Tense,
        val spaceIndex: Int
    )

    enum class AnswerState {
        NONE,
        RIGHT,
        WRONG,
        WRONG_SIGNAL
    }

    companion object {
        val TIP_MODE_OFF = TipMode(Tense.PRESENT_SIMPLE, -1)
    }
}