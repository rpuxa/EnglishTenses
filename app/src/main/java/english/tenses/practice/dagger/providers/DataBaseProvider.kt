package english.tenses.practice.dagger.providers

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import english.tenses.practice.model.ComplaintSender
import english.tenses.practice.model.db.DataBase
import english.tenses.practice.model.db.migrations.MigrationFrom1To2
import javax.inject.Singleton


@Module
class DataBaseProvider {

    @Provides
    @Singleton
    fun db(context: Context) = Room.databaseBuilder(context, DataBase::class.java, "database.db")
        .addMigrations(MigrationFrom1To2)
        .build()

    @Provides
    fun learnedSentencesDao(db: DataBase) = db.learnedSentencesDao

    @Provides
    fun correctnessStatisticDao(db: DataBase) = db.correctnessStatisticDao

    @Provides
    fun achievement(db: DataBase) = db.achievementDao

    @Provides
    fun translates(db: DataBase) = db.translatesDao

    @Provides
    fun sentences(db: DataBase) = db.sentencesDao

    @Provides
    fun answers(db: DataBase) = db.answersDao

    @Provides
    fun learnedSentencesDao2(db: DataBase) = db.learnedSentencesDao2

    @Provides
    @Singleton
    fun complaintSender() = ComplaintSender()
}