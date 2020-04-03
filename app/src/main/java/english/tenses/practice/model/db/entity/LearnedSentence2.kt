package english.tenses.practice.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learned_sentences2")
class LearnedSentence2(@PrimaryKey val id: Int, val tenseMask: Int)