package english.tenses.practice.model.db

import androidx.room.Entity

@Entity(tableName = "learned_sentences", primaryKeys = ["id", "tenseCode"])
class LearnedSentenceEntity(
    val id: Int,
    val tenseCode: Int
)