package english.tenses.practice.view

import android.app.Application
import android.util.Log
import android.util.SparseIntArray
import androidx.core.util.forEach
import androidx.core.util.set
import com.chibatching.kotpref.Kotpref
import english.tenses.practice.dagger.Component
import english.tenses.practice.dagger.DaggerComponent
import english.tenses.practice.dagger.providers.ContextProvider
import english.tenses.practice.model.logic.AssetsLoader
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.logic.SentenceStatistic
import english.tenses.practice.model.db.entity.LearnedSentence2
import english.tenses.practice.model.db.dao.LearnedSentencesDao2
import english.tenses.practice.model.db.dao.LearnedSentencesDao
import english.tenses.practice.model.db.dao.SentencesDao
import english.tenses.practice.model.enums.Language
import english.tenses.practice.model.logic.Translator
import english.tenses.practice.toMask
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var prefs: Prefs

    @Inject
    lateinit var oldLearnedSentencesDao: LearnedSentencesDao

    @Inject
    lateinit var learnedSentencesDao: LearnedSentencesDao2

    @Inject
    lateinit var assetsLoader: AssetsLoader

    @Inject
    lateinit var sentenceStatistic: SentenceStatistic

    @Inject
    lateinit var sentencesDao: SentencesDao

    @Inject
    lateinit var translator: Translator

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "APP CREATED!")
        Kotpref.init(this)
        component = DaggerComponent.builder()
            .contextProvider(ContextProvider(this))
            .build()
        component.inject(this)

        migrateLearnedSentences()
        sentenceStatistic.load()
        translator.load(Language[prefs.nativeLanguage])
        prefs.appOpens++
    }

    private fun migrateLearnedSentences() {
        runBlocking {
            val all = oldLearnedSentencesDao.getAll()
            if (all.isEmpty()) return@runBlocking
            val map = SparseIntArray()
            all.forEach {
                val id = assetsLoader.newIds[it.tenseCode][it.id]
                map[id] = map[id] or toMask(it.tenseCode)
            }
            val list = ArrayList<LearnedSentence2>()
            map.forEach { key, value ->
                list += LearnedSentence2(
                    key,
                    value
                )
            }
            learnedSentencesDao.insert(list)
            oldLearnedSentencesDao.clearAll()
        }
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