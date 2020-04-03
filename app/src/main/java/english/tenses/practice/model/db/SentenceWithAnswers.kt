package english.tenses.practice.model.db

import androidx.room.Embedded
import androidx.room.Relation

class SentenceWithAnswers(
    @Embedded
    val sentence: SentenceEntity,
    @Relation(parentColumn = "id", entityColumn = "sentenceId")
    val answers: List<AnswerEntity>
)