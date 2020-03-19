package ru.rpuxa.englishtenses.dagger

import dagger.Component
import ru.rpuxa.englishtenses.viewmodel.ViewModelFactory
import ru.rpuxa.englishtenses.dagger.providers.ContextProvider
import ru.rpuxa.englishtenses.dagger.providers.DataBaseProvider
import ru.rpuxa.englishtenses.dagger.providers.MainProvider
import ru.rpuxa.englishtenses.dagger.providers.ViewModelsProvider
import ru.rpuxa.englishtenses.model.SentenceStatistic
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextProvider::class,
        MainProvider::class,
        DataBaseProvider::class,
        ViewModelsProvider::class
    ]
)
interface Component {
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(viewModelFactory: SentenceStatistic)
}