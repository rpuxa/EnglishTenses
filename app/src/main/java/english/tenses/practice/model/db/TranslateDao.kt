package english.tenses.practice.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TranslateDao {

    @Query("SELECT * FROM translates WHERE sentence = :sentence")
    suspend fun get(sentence: String): TranslateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TranslateEntity)
}