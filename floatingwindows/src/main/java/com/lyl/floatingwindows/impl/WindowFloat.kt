package com.lyl.floatingwindows.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import com.lyl.floatingwindows.FloatConfig
import com.lyl.floatingwindows.FloatWindowFactory
import com.lyl.floatingwindows.view.MoveAndClickLayout

class WindowFloat(floatConfig: FloatConfig) : BaseFloat(floatConfig) {

    private lateinit var mLayoutParams: WindowManager.LayoutParams
    private lateinit var mWindowManager: WindowManager

    override fun moveChangeXY(x: Float, y: Float) {
        floatConfig.x = mLayoutParams.x + x
        floatConfig.y = mLayoutParams.y + y
        update()
    }

    override fun update() {
        mLayoutParams.x = floatConfig.x.toInt()
        mLayoutParams.y = floatConfig.y.toInt()
        mWindowManager.updateViewLayout(mView, mLayoutParams)
    }

    private fun addFloatView(activity: Activity) {
        mWindowManager =
            FloatWindowFactory.application!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        mLayoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mLayoutParams.format = PixelFormat.TRANSPARENT
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mLayoutParams.x = floatConfig.x.toInt()
        mLayoutParams.y = floatConfig.y.toInt()
        // 将悬浮窗控件添加到WindowManager
        this.mView = MoveAndClickLayout(FloatWindowFactory.application)
        this.mView?.addView(mChildView)
        if (mView != null) {
            mView?.dragFloatListener = this
            mWindowManager.addView(mView, mLayoutParams)
        }
    }


    private fun startOverlayPermission(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION
        intent.data = Uri.parse("package:" + activity.applicationInfo.packageName)
        activity.startActivityForResult(
            intent,
            9527
        )
    }


    override fun show(activity: Activity) {
        if (canDrawOverlays(FloatWindowFactory.application!!)) {
            addFloatView(activity)
        } else {
            startOverlayPermission(activity)
        }
    }


    private fun canDrawOverlays(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Settings.canDrawOverlays(context)
        } else {
            if (Settings.canDrawOverlays(context)) return true
            try {
                val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    ?: return false
                //getSystemService might return null
                val viewToAdd = View(context)
                val params = WindowManager.LayoutParams(
                    0,
                    0,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT
                )
                viewToAdd.layoutParams = params
                mgr.addView(viewToAdd, params)
                mgr.removeView(viewToAdd)
                return true
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            false
        }
    }
}