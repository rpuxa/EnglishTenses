package english.tenses.practice.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface LearnedSentencesDao2 {
    @Insert
    suspend fun insert(sentences: List<LearnedSentence2>)

    @Insert
    suspend fun insert(sentence: LearnedSentence2)

    @Query("SELECT * FROM learned_sentences2")
    suspend fun getAll(): Array<LearnedSentence2>

    @Query("DELETE FROM learned_sentences2 WHERE (tenseMask & :tenseMask) != 0")
    suspend fun clear(tenseMask: Int)
}