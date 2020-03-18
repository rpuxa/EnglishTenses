package ru.rpuxa.englishtenses.view.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.sendBlocking

open class DummyView : View, ResizableView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRef: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRef
    )

    private var mWidth = 0
    private var mHeight = 0


    override fun setWidth(width: Int) {
        mWidth = width
    }

    override fun setHeight(height: Int) {
        mHeight = height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mWidth, mHeight)
    }

    fun follow(listener: FollowListener) {
        addOnLayoutChangeListener(listener)
        requestLayout()
    }

    fun stopFollowing(listener: FollowListener) {
        removeOnLayoutChangeListener(listener)
        listener.close()
    }

    abstract class FollowListener(private val dummyView: DummyView, scope: CoroutineScope) :
        OnLayoutChangeListener {

        private val channel = Channel<Unit>(Channel.UNLIMITED)
        private var startTime = 0L
        private val job = SupervisorJob(scope.coroutineContext[Job])

        init {
            scope.launch(Dispatchers.Default + job) {
                Log.d(TAG, "Coroutine started: ${hashCode()}")
                try {
                    while (isActive) {
                        channel.receive()
                        Log.d(TAG, "Start ${dummyView.hashCode()}")
                        while (isActive) {
                            val (startTime, updateStartPos) = synchronized(this) {
                                val tmp = startTime
                                val update = tmp > 0
                                if (tmp > 0)
                                    startTime = -tmp
                                -startTime to update
                            }
                            val percent =
                                (System.currentTimeMillis() - startTime).toDouble() / ANIMATION_DURATION
                            onMove(dummyView, percent.coerceAtMost(1.0), updateStartPos)
                            if (percent >= 1) break
                        }
                    }
                } catch (e: CancellationException) {
                    Log.d(TAG, "Coroutine killed: ${hashCode()}  $isActive")
                }
            }
        }

        protected abstract suspend fun onMove(
            dummyView: DummyView,
            percent: Double,
            updateStartPos: Boolean
        )

        override fun onLayoutChange(
            v: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            synchronized(this) {
                startTime = System.currentTimeMillis()
            }
            Log.d(TAG, "Sent ${dummyView.hashCode()}")
            channel.sendBlocking(Unit)
        }

        fun close() {
            job.cancel()
        }
    }

    companion object {
        const val ANIMATION_DURATION = 300.0
        val TAG = DummyView::class.simpleName
    }
}