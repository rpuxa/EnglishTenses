@file:Suppress("EXPERIMENTAL_API_USAGE")

package english.tenses.practice.model.logic

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import english.tenses.practice.dataBaseFlow
import english.tenses.practice.loadDataBase
import english.tenses.practice.model.db.Prefs
import english.tenses.practice.model.db.dao.AnswersDao
import english.tenses.practice.model.db.dao.SentencesDao
import english.tenses.practice.model.db.dao.TranslatesDao
import english.tenses.practice.model.db.entity.AnswerEntity
import english.tenses.practice.model.db.entity.SentenceEntity
import english.tenses.practice.model.db.entity.TranslateEntity
import english.tenses.practice.model.enums.Language
import english.tenses.practice.model.enums.Person
import english.tenses.practice.model.enums.Tense
import english.tenses.practice.nextString
import english.tenses.practice.toMask
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RemoteSentenceLoader(
    private val prefs: Prefs,
    private val sentencesDao: SentencesDao,
    private val answersDao: AnswersDao,
    private val translator: Translator
) {

    fun load(): Flow<Float> {
        Log.d(TAG, "Start loading...")
        val wait = prefs.sentencesHash.isEmpty()
        return flow {
            if (wait) {
                Log.d(TAG, "Waiting...")
                coroutineScope {
                    val hash = loadDataBase("hash")
                    Log.d(TAG, "Hash loaded...")
                    emitAll(update(hash))
                }
            }
            GlobalScope.launch {
                try {
                    Log.d(TAG, "Collecting hash...")
                    dataBaseFlow("hash").collect {
                        Log.d(TAG, "New hash $it")
                        if (prefs.sentencesHash != it) {
                            Log.d(TAG, "Hashes are different")
                            update(it)
                        }
                    }
                } finally {
                    Log.d(TAG, "Hash collected")
                }
            }
            emit(1f)
        }
    }

    private fun CoroutineScope.update(newHash: String) = produce<Float>(capacity = Channel.UNLIMITED) {
        Log.d(TAG, "Updating...")
        val total = 4f
        var result = 0f
        suspend fun count() {
            val progress = ++result / total
            send(progress)
            Log.d(TAG, "Progress $progress")
        }
        count()
        val sentencesString = loadDataBase("sentences")
        count()
        translator.updateTranslations(Language[prefs.nativeLanguage], true)
        count()
        val (sentences, answers) = sentencesString.parseSentences()
        sentencesDao.update(sentences)
        answersDao.update(answers)
        prefs.sentencesHash = newHash
        count()
        Log.d(TAG, "Update done!")
    }

    private fun String.parseSentences(): Pair<List<SentenceEntity>, List<AnswerEntity>> {
        val iterator = iterator()
        val builder = StringBuilder()
        val sentences = ArrayList<SentenceEntity>()
        val answers = ArrayList<AnswerEntity>()

        while (iterator.hasNext()) {
            val id = iterator.nextString(builder, '#').toInt()
            val text = iterator.nextString(builder, '#')
            var mask = 0

            while (true) {
                val infinitive = iterator.nextString(builder, '#')
                if (infinitive.isEmpty()) break
                val correct = iterator.nextString(builder, '#')
                val tense = Tense[iterator.nextString(builder, '#').toInt(16)]
                val verb = iterator.nextString(builder, '#')
                val subject = iterator.nextString(builder, '#')
                val person = Person[iterator.nextString(builder, '@').toInt()]

                answers += AnswerEntity(
                    id, infinitive, correct, tense, verb, subject, person
                )
                mask = mask or toMask(tense.code)
            }
            sentences += SentenceEntity(
                id,
                text,
                mask
            )
        }

        return sentences to answers
    }

    companion object {
        private const val TAG = "RemoteSentenceLoaderD"
    }
}


