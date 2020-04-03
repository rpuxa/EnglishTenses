package english.tenses.practice.model.pojo

import english.tenses.practice.model.enums.SentenceItem
import english.tenses.practice.model.enums.WordAnswer

data class Sentence(val id: Int, val items: List<SentenceItem>) {
    val answers get() = items.filterIsInstance<WordAnswer>()
}
