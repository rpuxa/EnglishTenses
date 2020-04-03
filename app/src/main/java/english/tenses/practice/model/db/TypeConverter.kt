package english.tenses.practice.model.db

import androidx.room.TypeConverter
import english.tenses.practice.model.enums.Language
import english.tenses.practice.model.enums.Person
import english.tenses.practice.model.enums.Tense

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
    fun Language.toInt() = code

    @TypeConverter
    fun String.toLanguages() = Language[this]
}