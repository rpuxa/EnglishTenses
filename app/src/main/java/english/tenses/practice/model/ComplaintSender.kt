package english.tenses.practice.model

import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplaintSender {

    fun send(text: String) {
        val db = Firebase.firestore
        db.collection("complaints")
            .add(
                mapOf(
                    "complaint" to text
                )
            )
            .addOnCompleteListener {
                Log.d(TAG, "OnComplete. Exception: ${it.exception}. Is complete ${it.isComplete}")
            }
            .addOnFailureListener {
                Log.d(TAG, "OnCanceled $it")
            }
    }

    companion object {
        private const val TAG = "ComplaintSenderDebug"
    }
}