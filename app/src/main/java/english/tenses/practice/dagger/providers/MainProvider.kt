package english.tenses.practice.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import english.tenses.practice.model.*
import english.tenses.practice.model.db.*
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
    fun assetLoader(context: Context) = AssetsLoader(context)

    @Singleton
    @Provides
    fun sentenceStatistic(l: LearnedSentencesDao2, c: CorrectnessStatisticDao, s: SentencesDao) = SentenceStatistic(l, c, s)

    @Singleton
    @Provides
    fun remote(prefs: Prefs, sentencesDao: SentencesDao, translatesDao: TranslatesDao, answersDao: AnswersDao) =
        RemoteSentenceLoader(prefs, sentencesDao, translatesDao, answersDao)

}