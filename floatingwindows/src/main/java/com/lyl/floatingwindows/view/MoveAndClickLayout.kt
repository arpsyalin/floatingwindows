package com.lyl.floatingwindows.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import com.lyl.floatingwindows.interf.IDragFloatListener
import kotlin.math.abs

class MoveAndClickLayout @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context!!, attrs, defStyleAttr) {
    private var scrolling = false
    var isInOtherApp = true
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = getXByEvent(event)
                mY = getYByEvent(event)
                touchDownX = getXByEvent(event)
                touchDownY = getYByEvent(event)
                scrolling = false
            }
            MotionEvent.ACTION_MOVE -> scrolling =
                abs(touchDownX - getXByEvent(event)) >= ViewConfiguration.get(
                    context
                ).scaledTouchSlop || abs(touchDownY - getYByEvent(event)) >= ViewConfiguration.get(
                    context
                ).scaledTouchSlop
            MotionEvent.ACTION_UP -> scrolling = false
        }
        return scrolling
    }

    private fun getXByEvent(event: MotionEvent): Float {
        return if (isInOtherApp) event.rawX else event.x
    }

    private fun getYByEvent(event: MotionEvent): Float {
        return if (isInOtherApp) event.rawY else event.y
    }

    private var touchDownX = 0f
    private var touchDownY = 0f
    var dragFloatListener: IDragFloatListener? = null
    private var mX = 0f
    private var mY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_MOVE -> {
                val nowX = getXByEvent(event)
                val nowY = getYByEvent(event)
                val movedX = nowX - mX
                val movedY = nowY - mY
                mX = nowX
                mY = nowY
                if (dragFloatListener != null) {
                    dragFloatListener!!.onMoveChange(movedX, movedY)
                    return true
                }
            }
            MotionEvent.ACTION_UP -> if (dragFloatListener != null) {
                dragFloatListener!!.onMoveUp()
                return true
            }
            else -> {}
        }
        return super.onTouchEvent(event)
    }
}