package english.tenses.practice.viewmodel

import android.util.Log
import androidx.lifecycle.*
import english.tenses.practice.SingleLiveEvent
import english.tenses.practice.State
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.db.entity.CorrectnessStatistic
import english.tenses.practice.model.enums.*
import english.tenses.practice.model.logic.ComplaintSender
import english.tenses.practice.model.logic.SentenceStatistic
import english.tenses.practice.model.logic.Translator
import english.tenses.practice.model.pojo.ExerciseResult
import english.tenses.practice.model.pojo.Sentence
import english.tenses.practice.update
import english.tenses.practice.view.fragments.ExerciseFragment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseViewModel @Inject constructor(
    private val loader: SentenceStatistic,
    private val sentenceStatistic: SentenceStatistic,
    private val complaintSender: ComplaintSender,
    private val translator: Translator,
    private val prefs: Prefs
) : ViewModel() {


    private var tipsEnabled = false
    private val spacesStates = MutableLiveData<List<ExerciseFragment.SpaceState>>()
    private val answerStates = MutableLiveData<MutableList<AnswerState>>()
    private val _tipMode = State(TIP_MODE_OFF)
    private val startTime = System.currentTimeMillis()
    private val sentenceToString get() = sentence.items.joinToString(" ")
    private val _translate = MutableLiveData<String>()


    lateinit var sentence: Sentence
    val tipMode = _tipMode.liveData
    var shuffledAnswers = emptyList<String>()
        private set
    var rightAnswers = emptyList<String>()
        private set
    var result = State<ExerciseResult?>(null)
    val showTipButton: LiveData<Boolean> = spacesStates.map { tipsEnabled && it.any { it.empty } }
    val showIrregularVerbTable = SingleLiveEvent<String>()
    val translate: LiveData<String> get() = _translate

    val answerListener = object : ExerciseFragment.AnswersMoveListener {
        override fun onMoveToSpace(answerId: Int, spaceId: Int): Boolean {

            spacesStates.value!![spaceId].let {
                if (!it.empty && answerStates.value!![it.answerId].right) {
                    return false
                }
            }

            if (_tipMode.value != TIP_MODE_OFF && _tipMode.value.spaceIndex == spaceId) {
                return if (rightAnswers[spaceId] == shuffledAnswers[answerId]) {
                    answerStates.update { this[answerId] =
                        Result(
                            correct = true,
                            block = true
                        )
                    }
                    _tipMode.value = TIP_MODE_OFF
                    if (spacesStates.value!!.all { it.spaceId == spaceId || !it.empty && answerStates.value!![it.answerId].right }) {
                        setResult(sentence.answers, emptyList())
                    }
                    true
                } else {
                    answerStates.update { this[answerId] =
                        WrongSignal
                    }
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
        sentence = testSentence ?: runBlocking {
            val start = System.currentTimeMillis()
            val result= loader.nextSentence(set)
            Log.d("LoadingSentenceTime", (System.currentTimeMillis() - start).toString())
            result
        }

        val answers = sentence.answers
        shuffledAnswers = answers.flatMap {
            (it.wrongVariants + it.correctForm).shuffled()
        }
        rightAnswers = answers.map { it.correctForm }

        answerStates.value = MutableList(shuffledAnswers.size) {
            None()
        }

        spacesStates.value = List(answers.size) { ExerciseFragment.SpaceState(it) }
    }

    fun showTranslate(): Boolean = prefs.nativeLanguage != Language.ENGLISH.code


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
                    this[it.answerId] =
                        Result(
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
        val usedRightAnswers = HashSet<Int>()
        val rightAnswersIndexes = ArrayList<Int>()

        answerStates.update {
            repeat(size) {
                val answer = shuffledAnswers[it]
                var index = -1
                for (i in rightAnswers.indices) {
                    if (i !in usedRightAnswers && rightAnswers[i] == answer) {
                        index = i
                        usedRightAnswers += i
                        break
                    }
                }
                if (index == -1) {
                    this[it] = None(true)
                } else {
                    rightAnswersIndexes += it
                    this[it] = Result(
                        true,
                        block = true
                    )
                }
            }
        }

        return rightAnswersIndexes
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
                        correctness[it.tense] ?: CorrectnessStatistic(
                            it.tense.code,
                            0,
                            0
                        )
                    statistic.addResult(correct)
                    correctness[it.tense] = statistic
                }
            }
            add(correct, true)
            add(incorrect, false)
            val exerciseResult =
                ExerciseResult(
                    correctness.values.toList(),
                    incorrect.isEmpty(),
                    System.currentTimeMillis() - startTime
                )

            sentenceStatistic.addResult(exerciseResult.result)
            result.value = exerciseResult
        }
    }

    fun irregularVerbsClick() {
        val query = sentence.answers
            .mapNotNull { it.irregular?.first }
            .toSet()
            .joinToString(", ")

        showIrregularVerbTable.value = query
    }

    fun sendComplaint() {
        complaintSender.send(sentenceToString)
    }

    fun translate() {
        if (translate.value != null) return

        viewModelScope.launch {
            _translate.value = translator.translate(sentence.id)
        }
    }

    class TipMode(
        val tense: Tense,
        val spaceIndex: Int
    )


    companion object {
        val TIP_MODE_OFF = TipMode(Tense.PRESENT_SIMPLE, -1)
        private val testSentence: Sentence? =null /*Sentence(
            listOf(
                Word("Hi"),
                WordAnswer("read", listOf("read"), Tense.PAST_SIMPLE, null, listOf("read")),
                WordAnswer("read", listOf("read"), Tense.PAST_SIMPLE, null, listOf("read"))
            )
        )*/
    }
}