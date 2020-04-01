package english.tenses.practice.view

import android.app.Application
import android.util.Log
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
        Log.d(TAG, "APP CREATED!")
        Kotpref.init(this)
        component = DaggerComponent.builder()
            .contextProvider(ContextProvider(this))
            .build()


        component.inject(this)

        prefs.appOpens++
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(TAG, "APP LOW MEMORY!")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(TAG, "APP TRIM MEMORY!")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "APP DESTROYED!")
    }

    companion object {
        lateinit var component: Component

        const val TAG = "ApplicationDebug"
    }
}