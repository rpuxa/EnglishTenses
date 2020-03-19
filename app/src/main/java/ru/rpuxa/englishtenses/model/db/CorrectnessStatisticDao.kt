package ru.rpuxa.englishtenses.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CorrectnessStatisticDao {

    @Query("SELECT * FROM correctness_statistic WHERE tenseCode = :tenseCode")
    suspend fun get(tenseCode: Int): CorrectnessStatistic?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(statistic: CorrectnessStatistic)

    @get:Query("SELECT * FROM correctness_statistic")
    val liveData: LiveData<List<CorrectnessStatistic>>
}