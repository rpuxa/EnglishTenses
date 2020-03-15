package ru.rpuxa.englishtenses.model

data class IrregularVerb(
    val id: Int,
    val first: String,
    val second: List<String>,
    val third: List<String>
) {
    val secondText = second.txt()
    val thirdText = third.txt()

    private fun List<String>.txt() = joinToString(" / ")
}