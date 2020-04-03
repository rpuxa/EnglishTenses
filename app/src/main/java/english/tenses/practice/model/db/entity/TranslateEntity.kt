package english.tenses.practice.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import english.tenses.practice.model.enums.Languages
import english.tenses.practice.model.db.TypeConverter

@Entity(tableName = "translates")
@TypeConverters(TypeConverter::class)
class TranslateEntity(
    val sentenceId: Int,
    val language: Languages,
    val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}