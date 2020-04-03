package english.tenses.practice.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import english.tenses.practice.model.AnswersDao

@Database(
    entities = [
        LearnedSentenceEntity::class,
        CorrectnessStatistic::class,
        AchievementEntity::class,
        SentenceEntity::class,
        AnswerEntity::class,
        TranslateEntity::class,
        LearnedSentence2::class
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
}