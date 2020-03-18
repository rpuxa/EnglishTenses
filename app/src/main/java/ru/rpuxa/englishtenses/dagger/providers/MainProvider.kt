package ru.rpuxa.englishtenses.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rpuxa.englishtenses.model.Prefs
import ru.rpuxa.englishtenses.model.SentenceLoader
import ru.rpuxa.englishtenses.model.SentencesHandler
import ru.rpuxa.englishtenses.model.User
import ru.rpuxa.englishtenses.model.db.LearnedSentencesDao
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
    fun sentenceLoader(context: Context, sentencesHandler: SentencesHandler) = SentenceLoader(context, sentencesHandler)

    @Singleton
    @Provides
    fun sentenceHandler(l: LearnedSentencesDao) = SentencesHandler(l)
}