package english.tenses.practice.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import english.tenses.practice.R
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.db.dao.*
import english.tenses.practice.model.logic.*
import javax.inject.Singleton

@Module
class MainProvider {

    @Singleton
    @Provides
    fun prefs() = Prefs()

    @Singleton
    @Provides
    fun assetLoader(context: Context) =
        AssetsLoader(context)

    @Singleton
    @Provides
    fun sentenceStatistic(l: LearnedSentencesDao2, c: CorrectnessStatisticDao, s: SentencesDao) =
        SentenceStatistic(l, c, s)

    @Provides
    @Singleton
    fun complaintSender() = ComplaintSender()

    @Provides
    @Singleton
    fun remote(prefs: Prefs, sentencesDao: SentencesDao, translatesDao: TranslatesDao, answersDao: AnswersDao) =
        RemoteSentenceLoader(
            prefs,
            sentencesDao,
            translatesDao,
            answersDao
        )

    @Provides
    @Singleton
    fun translator(
    ) = Translator(
    )
}