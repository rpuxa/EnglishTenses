package english.tenses.practice.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import english.tenses.practice.model.db.AnswerEntity

@Dao
abstract class AnswersDao {

    @Insert
    protected abstract suspend fun insert(list: List<AnswerEntity>)

    @Query("DELETE FROM answers")
    protected abstract suspend fun clear()

    @Transaction
    open suspend fun update(newList: List<AnswerEntity>) {
        clear()
        insert(newList)
    }
}