package english.tenses.practice.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import english.tenses.practice.model.db.migrations.MigrationFrom1To2

@Database(
    entities = [
        LearnedSentenceEntity::class,
        CorrectnessStatistic::class,
        AchievementEntity::class,
        TranslateEntity::class
    ],
    version = 2
)
abstract class DataBase : RoomDatabase() {
    abstract val learnedSentencesDao: LearnedSentencesDao
    abstract val correctnessStatisticDao: CorrectnessStatisticDao
    abstract val achievementDao: AchievementDao

    abstract val translatesDao: TranslateDao

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(context, DataBase::class.java, "database.db")
                .addMigrations(MigrationFrom1To2)
                .build()
    }
}

