package english.tenses.practice.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import english.tenses.practice.model.Person
import english.tenses.practice.model.Tense

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