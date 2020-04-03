package english.tenses.practice.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import english.tenses.practice.model.db.entity.TranslateEntity

@Dao
abstract class TranslatesDao {

    @Insert
    protected abstract suspend fun insert(list: List<TranslateEntity>)

    @Query("DELETE FROM translates")
    protected abstract suspend fun clear()

    @Transaction
    open suspend fun update(list: List<TranslateEntity>) {
        clear()
        insert(list)
    }
}