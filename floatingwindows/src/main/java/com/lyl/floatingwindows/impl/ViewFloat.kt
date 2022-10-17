package com.lyl.floatingwindows.impl

import android.R
import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.lyl.floatingwindows.FloatConfig
import com.lyl.floatingwindows.view.MoveAndClickLayout

class ViewFloat(floatConfig: FloatConfig) : BaseFloat(floatConfig) {
    lateinit var mLayoutParams: FrameLayout.LayoutParams

    override fun show(activity: Activity) {
        val rootView = activity.findViewById<ViewGroup>(R.id.content)
        mLayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        mView = MoveAndClickLayout(activity)
        mView?.isInOtherApp = true
        mView?.x = floatConfig.x
        mView?.y = floatConfig.y
        mView?.addView(mChildView)
        mView?.dragFloatListener = this
        rootView.addView(mView, mLayoutParams)
    }

    override fun moveChangeXY(x: Float, y: Float) {
        floatConfig.x = mView!!.x + x
        floatConfig.y = mView!!.y + y
        update()
    }

    override fun update() {
        mView?.x = floatConfig.x
        mView?.y = floatConfig.y
        mView?.invalidate()

    }
}