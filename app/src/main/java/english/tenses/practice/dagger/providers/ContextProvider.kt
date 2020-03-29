package english.tenses.practice.dagger.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import english.tenses.practice.view.App


@Module
class ContextProvider(private val app: App) {

    @Provides
    fun context(): Context = app
}