package english.tenses.practice.model

sealed class AnswerState(var block: Boolean)

class None(block: Boolean = false) : AnswerState(block)
class Result(val correct: Boolean, block: Boolean = false) : AnswerState(block)
object WrongSignal : AnswerState(false)

val AnswerState.right get() = this is Result && correct
val AnswerState.wrong get() = this is Result && !correct