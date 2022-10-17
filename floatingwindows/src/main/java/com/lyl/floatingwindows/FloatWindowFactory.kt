package com.lyl.floatingwindows

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.lyl.floatingwindows.impl.ViewFloat
import com.lyl.floatingwindows.impl.WindowFloat
import com.lyl.floatingwindows.interf.IFloatWindow

class FloatWindowFactory {
    var viewFloats = mutableListOf<IFloatWindow>()
    var windowFloats = mutableListOf<IFloatWindow>()
    var callBack = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    }

    fun buildFloatWindow(floatConfig: FloatConfig): IFloatWindow {
        return if (floatConfig.onOtherApp) {
            var n = WindowFloat(floatConfig)
            windowFloats += n
            n
        } else {
            var n = ViewFloat(floatConfig)
            viewFloats += n
            n
        }
    }


    companion object {
        val sInstance by lazy(LazyThreadSafetyMode.NONE) {
            FloatWindowFactory()
        }
        var application: Application? = null
        var sDW = 720
        var sDH = 1080


        @JvmStatic
        fun init(app: Application) {
            if (application == null) {
                application = app
                application?.registerActivityLifecycleCallbacks(sInstance.callBack)
                initWH()
            }
        }

        private fun initWH() {
            var mWindowManager =
                application?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                sDW = mWindowManager.currentWindowMetrics.bounds.width()
                sDH = mWindowManager.currentWindowMetrics.bounds.height()
            } else {
                sDW = mWindowManager.defaultDisplay.width
                sDH = mWindowManager.defaultDisplay.height
            }
        }

        @JvmStatic
        fun unInit() {
            if (application != null) {
                application?.unregisterActivityLifecycleCallbacks(sInstance.callBack)
                application = null
            }
        }
    }

}