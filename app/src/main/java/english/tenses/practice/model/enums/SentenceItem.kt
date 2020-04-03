package english.tenses.practice.model.enums


sealed class SentenceItem

class Word(val text: String) : SentenceItem() {
    override fun toString(): String  = text
}

data class WordAnswer(
    val infinitive: String,
    val correctForms: List<String>,
    val tense: Tense,
    val irregular: IrregularVerb?,
    val wrongVariants: List<String>
) : SentenceItem() {
    val correctForm get() = correctForms.first()

    override fun toString(): String  = correctForm
}