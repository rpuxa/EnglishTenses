package ru.rpuxa.englishtenses.parser

import androidx.room.Dao
import androidx.room.RoomDatabase

abstract class DB : RoomDatabase() {

}

@Dao
interface SentencesDao {

}