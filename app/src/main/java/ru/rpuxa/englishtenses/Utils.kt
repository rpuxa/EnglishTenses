package ru.rpuxa.englishtenses

import android.content.res.Resources
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
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
import ru.rpuxa.chinese.viewmodel.ViewModelFactory
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

inline fun <T> MutableLiveData<T>.update(block: T.() -> Unit = {}) {
    val v = value
    v!!.block()
    value = v
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




inline fun <reified VM : ViewModel> ComponentActivity.viewModel() =
    viewModels<VM>(::ViewModelFactory)

inline fun <reified VM : ViewModel> Fragment.viewModel() =
    activityViewModels<VM>(::ViewModelFactory)

inline fun <reified VM : ViewModel> Fragment.fragmentViewModel() =
    viewModels<VM>(factoryProducer = ::ViewModelFactory)


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

fun Int.sqr() = this * this

infix fun Point.dist(other: Point) = sqrt(((other.x - x).sqr() + (other.y - y).sqr()).toFloat())