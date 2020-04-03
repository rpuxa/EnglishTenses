package english.tenses.practice.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import english.tenses.practice.model.Tense
import english.tenses.practice.toMask

@Dao
abstract class SentencesDao {

    @Insert
    protected abstract suspend fun insert(list: List<SentenceEntity>)

    @Query("DELETE FROM sentences")
    protected abstract suspend fun clear()

    @Query("SELECT COUNT(*) FROM sentences WHERE (tenseMask & :tenseMask) != 0")
    protected abstract suspend fun countByTense(tenseMask: Int): Long

    @Query("SELECT * FROM sentences WHERE id = :id")
    abstract suspend fun getById(id: Int): SentenceWithAnswers

    @Query("SELECT (id) FROM sentences WHERE (tenseMask & :tenseMask) != 0")
    abstract suspend fun getAllByTense(tenseMask: Int): IntArray

    @Transaction
    open suspend fun update(newList: List<SentenceEntity>) {
        clear()
        insert(newList)
    }

    @Transaction
    open suspend fun sizes() = List(Tense.values().size) {
        countByTense(toMask(it)).toInt()
    }
}