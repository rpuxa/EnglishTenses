package english.tenses.practice.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import english.tenses.practice.model.db.entity.TranslateEntity
import english.tenses.practice.model.enums.Language

@Dao
abstract class TranslatesDao {

    @Query("SELECT * FROM translates WHERE language = :language")
    abstract suspend fun getRandom(language: Language): TranslateEntity?

    @Query("SELECT * FROM translates WHERE sentenceId = :sentenceId AND language = :language")
    abstract suspend fun get(language: Language, sentenceId: Int): TranslateEntity

    @Insert
    protected abstract suspend fun insert(list: List<TranslateEntity>)

    @Query("DELETE FROM translates")
    protected abstract suspend fun clear()

    @Transaction
    open suspend fun update(list: List<TranslateEntity>, clear: Boolean) {
        if (clear) {
            clear()
        }
        insert(list)
    }
}