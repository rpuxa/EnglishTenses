package ru.rpuxa.englishtenses.view

import android.app.Application
import com.chibatching.kotpref.Kotpref
import ru.rpuxa.englishtenses.dagger.Component
import ru.rpuxa.englishtenses.dagger.DaggerComponent
import ru.rpuxa.englishtenses.dagger.providers.ContextProvider

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        component = DaggerComponent.builder()
            .contextProvider(ContextProvider(this))
            .build()
    }


    companion object {
        lateinit var component: Component
    }
}