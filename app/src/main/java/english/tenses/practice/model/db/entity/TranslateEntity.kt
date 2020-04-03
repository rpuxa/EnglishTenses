package english.tenses.practice.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import english.tenses.practice.model.enums.Language
import english.tenses.practice.model.db.TypeConverter

@Entity(tableName = "translates")
@TypeConverters(TypeConverter::class)
class TranslateEntity(
    val sentenceId: Int,
    val language: Language,
    val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}