package english.tenses.practice.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translates")
class TranslateEntity(
    @PrimaryKey
    val sentence: String,
    val translate: String
)