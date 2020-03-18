package ru.rpuxa.englishtenses

import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.minus
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.viewmodel.ViewModelFactory
import kotlin.math.sqrt


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
import ru.rpuxa.englishtenses.viewmodel.ViewModelFactory
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

@Suppress("UNCHECKED_CAST")
fun <T> getEqualsDiff() = EqualsDiff as DiffUtil.ItemCallback<T>

private object EqualsDiff : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Any, newItem: Any) =
        areItemsTheSame(oldItem, newItem)
}







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

fun event() = SingleLiveEvent<Unit>()