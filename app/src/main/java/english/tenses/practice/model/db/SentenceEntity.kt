package english.tenses.practice.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "sentences")
class SentenceEntity(
    @PrimaryKey
    val id: Int,
    val text: String,
    val tenseMask: Int
)

