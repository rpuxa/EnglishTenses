package english.tenses.practice.dagger

import dagger.Component
import english.tenses.practice.viewmodel.ViewModelFactory
import english.tenses.practice.dagger.providers.ContextProvider
import english.tenses.practice.dagger.providers.DataBaseProvider
import english.tenses.practice.dagger.providers.MainProvider
import english.tenses.practice.dagger.providers.ViewModelsProvider
import english.tenses.practice.model.Achievement
import english.tenses.practice.model.SentenceStatistic
import english.tenses.practice.view.App
import english.tenses.practice.view.activities.SplashActivity
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
    fun inject(achievement: Achievement)
    fun inject(app: App)
    fun inject(splashActivity: SplashActivity)
}