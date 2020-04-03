package english.tenses.practice.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import english.tenses.practice.model.db.dao.*
import english.tenses.practice.model.db.entity.*
import english.tenses.practice.model.db.migrations.MigrationFrom1To2

@Database(
    entities = [
        LearnedSentenceEntity::class,
        CorrectnessStatistic::class,
        SentenceEntity::class,
        AnswerEntity::class,
        TranslateEntity::class,
        LearnedSentence2::class,
        AchievementEntity::class
    ],
    version = 2
)
abstract class DataBase : RoomDatabase() {
    abstract val learnedSentencesDao: LearnedSentencesDao
    abstract val correctnessStatisticDao: CorrectnessStatisticDao
    abstract val achievementDao: AchievementDao
    abstract val sentencesDao: SentencesDao
    abstract val translatesDao: TranslatesDao
    abstract val answersDao: AnswersDao
    abstract val learnedSentencesDao2: LearnedSentencesDao2

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(context, DataBase::class.java, "database.db")
                .addMigrations(MigrationFrom1To2)
                .build()
    }
}