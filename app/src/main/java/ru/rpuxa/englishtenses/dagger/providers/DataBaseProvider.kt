package ru.rpuxa.englishtenses.dagger.providers

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.rpuxa.englishtenses.model.db.DataBase
import javax.inject.Singleton


@Module
class DataBaseProvider {

    @Provides
    @Singleton
    fun db(context: Context) = Room.databaseBuilder(context, DataBase::class.java, "database.db").build()

    @Provides
    fun learnedSentencesDao(db: DataBase) = db.learnedSentencesDao

    @Provides
    fun correctnessStatisticDao(db: DataBase) = db.correctnessStatisticDao

    @Provides
    fun achievement(db: DataBase) = db.achievementDao
}