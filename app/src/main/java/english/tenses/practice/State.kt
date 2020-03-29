package english.tenses.practice

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class State<T>(
    value: T
) {
    private val mutableLiveData = MutableLiveData<T>(value)

    var value: T = value
        set(value) {
            field = value
            if (Looper.myLooper() == null) {
                mutableLiveData.postValue(value)
            } else {
                mutableLiveData.value = value
            }
        }

    val liveData: LiveData<T> get() = mutableLiveData

    inline fun update(block: (T) -> Unit) {
        val v = value
        block(v)
        value = v
    }
}