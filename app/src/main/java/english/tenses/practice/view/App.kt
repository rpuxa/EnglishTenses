package english.tenses.practice.view

import android.app.Application
import com.chibatching.kotpref.Kotpref
import english.tenses.practice.dagger.Component
import english.tenses.practice.dagger.DaggerComponent
import english.tenses.practice.dagger.providers.ContextProvider
import english.tenses.practice.model.Prefs
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var prefs: Prefs


    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        component = DaggerComponent.builder()
            .contextProvider(ContextProvider(this))
            .build()


        component.inject(this)

        prefs.appOpens++
    }


    companion object {
        lateinit var component: Component
    }
}