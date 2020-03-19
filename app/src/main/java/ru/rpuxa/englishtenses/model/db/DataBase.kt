package ru.rpuxa.englishtenses.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LearnedSentenceEntity::class,
        CorrectnessStatistic::class
    ],
    version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract val learnedSentencesDao: LearnedSentencesDao

    abstract val correctnessStatisticDao: CorrectnessStatisticDao
}