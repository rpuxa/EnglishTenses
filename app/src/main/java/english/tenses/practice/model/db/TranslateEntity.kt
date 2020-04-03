package english.tenses.practice.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import english.tenses.practice.model.Languages

@Entity(tableName = "translates")
@TypeConverters(TypeConverter::class)
class TranslateEntity(
    val sentenceId: Int,
    val language: Languages,
    val text: String
){
    @PrimaryKey(autoGenerate = true)
    var id=0
}