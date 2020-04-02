package english.tenses.practice.dagger.providers

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import english.tenses.practice.model.ComplaintSender
import english.tenses.practice.model.db.DataBase
import javax.inject.Singleton


@Module
class DataBaseProvider {

    @Provides
    @Singleton
    fun db(context: Context) = DataBase.create(context)

    @Provides
    fun learnedSentencesDao(db: DataBase) = db.learnedSentencesDao

    @Provides
    fun correctnessStatisticDao(db: DataBase) = db.correctnessStatisticDao

    @Provides
    fun achievement(db: DataBase) = db.achievementDao

    @Provides
    fun translates(db: DataBase) = db.translatesDao
}