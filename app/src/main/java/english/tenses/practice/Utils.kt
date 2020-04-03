@file:Suppress("EXPERIMENTAL_API_USAGE")

package english.tenses.practice

import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.minus
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import english.tenses.practice.model.enums.Tense
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import english.tenses.practice.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.sqrt
import kotlin.random.Random


/*
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import english.tenses.englishtenses.viewmodel.ViewModelFactory
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random


var <T> MutableLiveData<T>.nnValue: T
    @Deprecated("Use value!!")
    inline get() = value!!
    set(value) {
        if (Looper.myLooper() == null) {
            postValue(value)
        } else {
            setValue(value)
        }
    }



inline val Fragment.lazyNavController get() = lazy { findNavController() }









fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = currentFocus ?: return
    inputManager.hideSoftInputFromWindow(
        currentFocus.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun Activity.openKeyboard() {
    hideKeyboard()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}
*/

@Suppress("UNCHECKED_CAST")
fun <T> getEqualsDiff() = EqualsDiff as DiffUtil.ItemCallback<T>

private object EqualsDiff : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Any, newItem: Any) =
        areItemsTheSame(oldItem, newItem)
}

inline fun <T> MutableLiveData<T>.update(block: T.() -> Unit = {}) {
    val v = value
    v!!.block()
    value = v
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModel() =
    viewModels<VM>(::ViewModelFactory)

inline fun <reified VM : ViewModel> Fragment.viewModel() =
    activityViewModels<VM>(::ViewModelFactory)

inline fun <reified VM : ViewModel> Fragment.fragmentViewModel() =
    viewModels<VM>(factoryProducer = ::ViewModelFactory)

fun ViewGroup.inflate(@LayoutRes res: Int): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(res, this, false)
}

fun View.removeParent() {
    (parent as? ViewGroup)?.removeView(this)
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

inline fun View.onMeasured(crossinline block: () -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            block()
            return true
        }
    })
}

fun Point(x: Float, y: Float) = Point(x.toInt(), y.toInt())

operator fun Point.times(d: Double) = Point((x * d).toInt(), (y * d).toInt())

val ZERO = Point(0, 0)

val Point.length: Float
    get() = dist(ZERO)

fun Int.sqr() = this * this

infix fun Point.dist(other: Point) = sqrt(((other.x - x).sqr() + (other.y - y).sqr()).toFloat())


var View.coordinates: Point
    get() {
        val array = IntArray(2)
        getLocationOnScreen(array)
        return Point(array[0], array[1])
    }
    set(point) {
        val (x, y) = point - parentCoordinates
        setX(x.toFloat())
        setY(y.toFloat())
    }

val View.parentCoordinates: Point get() = (parent as View).coordinates

val View.viewRect: Rect
    get() {
        val (x, y) = coordinates
        return Rect(x, y, x + width, y + height)
    }

@ExperimentalCoroutinesApi
fun TextView.textChangeFlow() = callbackFlow {
    addTextChangedListener {
        offer(it.toString())
    }
    offer(text.toString())
    awaitClose()
}

fun View.updateParams(width: Int? = null, height: Int? = null) {
    updateLayoutParams {
        if (width != null)
            this.width = width
        if (height != null)
            this.height = height
    }
}

fun Iterable<Tense>.toMask(): Int {
    var mask = 0
    forEach {
        mask = mask or (1 shl it.code)
    }
    return mask
}

fun toMask(i: Int) = 1 shl i


fun <T> random(weights: List<Pair<T, Double>>): T {
    val sum = weights.sumByDouble { it.second }
    val chances = weights.map { it.second / sum }
    val randomDouble = Random.nextDouble()
    var d = 0.0
    val i = chances.indices.first {
        d += chances[it]
        randomDouble < d
    }
    return weights[i].first
}


suspend fun loadDataBase(name: String): String {
    return suspendCoroutine {
        Firebase.database.getReference(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    it.resumeWithException(p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    it.resume(p0.getValue<String>()!!)
                }
            })
    }
}

fun dataBaseFlow(name: String): Flow<String> =
    callbackFlow {
        Firebase.database.getReference(name)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    close(p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    offer(p0.getValue<String>()!!)
                }
            })
        awaitClose()
    }

fun CharIterator.nextString(builder: StringBuilder, delimiter: Char): String {
    while (hasNext()) {
        val char = next()
        if (char == delimiter)
            break
        builder.append(char)
    }
    val string = builder.toString()
    builder.clear()
    return string
}