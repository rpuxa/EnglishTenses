package english.tenses.practice.model.db

import androidx.room.TypeConverter
import english.tenses.practice.model.Languages
import english.tenses.practice.model.Person
import english.tenses.practice.model.Tense

class TypeConverter {

    @TypeConverter
    fun Person.toInt() = code

    @TypeConverter
    fun Int.toPerson() = Person[this]

    @TypeConverter
    fun Tense.toInt() = code

    @TypeConverter
    fun Int.toTense() = Tense[this]

    @TypeConverter
    fun Languages.toInt() = code

    @TypeConverter
    fun String.toLanguages() = Languages[this]
}