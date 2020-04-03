package english.tenses.practice.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import english.tenses.practice.model.enums.Person
import english.tenses.practice.model.enums.Tense
import english.tenses.practice.model.db.TypeConverter

@Entity(tableName = "answers")
@TypeConverters(TypeConverter::class)
class AnswerEntity(
    val sentenceId: Int,
    val infinitive: String,
    val correct: String,
    val tense: Tense,
    val verb: String,
    val subject: String,
    val person: Person
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}