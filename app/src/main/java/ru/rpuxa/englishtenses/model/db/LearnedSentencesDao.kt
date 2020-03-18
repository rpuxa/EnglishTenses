package ru.rpuxa.englishtenses.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LearnedSentencesDao {
    @Insert
    suspend fun insert(sentence: LearnedSentenceEntity)

    @Query("SELECT * FROM learned_sentences")
    suspend fun getAll(): List<LearnedSentenceEntity>

    @Query("DELETE FROM learned_sentences WHERE tenseCode = :tense")
    suspend fun clear(tense: Int)
}