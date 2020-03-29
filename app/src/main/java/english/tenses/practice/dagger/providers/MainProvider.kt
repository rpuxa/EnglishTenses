package english.tenses.practice.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import english.tenses.practice.model.*
import english.tenses.practice.model.db.CorrectnessStatisticDao
import english.tenses.practice.model.db.LearnedSentencesDao
import javax.inject.Singleton

@Module
class MainProvider {

    @Singleton
    @Provides
    fun prefs() = Prefs()

    @Singleton
    @Provides
    fun user(prefs: Prefs) = User(prefs)

    @Singleton
    @Provides
    fun sentenceLoader(context: Context, sentencesHandler: SentenceStatistic) = SentenceLoader(context, sentencesHandler)

    @Singleton
    @Provides
    fun sentenceStatistic(l: LearnedSentencesDao, c: CorrectnessStatisticDao) = SentenceStatistic(l, c)

}