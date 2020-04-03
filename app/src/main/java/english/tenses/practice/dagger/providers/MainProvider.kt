package english.tenses.practice.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import english.tenses.practice.R
import english.tenses.practice.model.*
import english.tenses.practice.model.db.CorrectnessStatisticDao
import english.tenses.practice.model.db.LearnedSentencesDao
import english.tenses.practice.model.db.TranslateDao
import english.tenses.practice.model.server.YandexTranslatorServer
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
    fun sentenceStatistic(l: LearnedSentencesDao, c: CorrectnessStatisticDao) =
        SentenceStatistic(l, c)

    @Provides
    @Singleton
    fun complaintSender() = ComplaintSender()

    @Provides
    @Singleton
    fun yandexTranslator() = YandexTranslatorServer.create()

    @Provides
    @Singleton
    fun remote(prefs: Prefs, sentencesDao: SentencesDao, translatesDao: TranslatesDao, answersDao: AnswersDao) =
        RemoteSentenceLoader(prefs, sentencesDao, translatesDao, answersDao)

    @Provides
    @Singleton
    fun translator(
        context: Context,
        translateDao: TranslateDao,
        server: YandexTranslatorServer
    ) = Translator(
        context.getString(R.string.translator_api_token),
        server,
        translateDao
    )
}