package ru.rpuxa.englishtenses.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rpuxa.englishtenses.model.IrregularVerbsHolder
import ru.rpuxa.englishtenses.model.Prefs
import ru.rpuxa.englishtenses.model.SentenceLoader
import ru.rpuxa.englishtenses.model.User
import javax.inject.Singleton

@Module
class MainProvider {

    @Singleton
    @Provides
    fun irregularVerbsHolder(context: Context) = IrregularVerbsHolder(context)

    @Singleton
    @Provides
    fun prefs() = Prefs()

    @Singleton
    @Provides
    fun user(prefs: Prefs) = User(prefs)

    @Singleton
    @Provides
    fun sentenceLoader(context: Context) = SentenceLoader(context)
}