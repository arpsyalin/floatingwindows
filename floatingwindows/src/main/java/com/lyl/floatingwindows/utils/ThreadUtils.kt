package com.lyl.floatingwindows.utils

import android.os.Handler
import android.os.Looper

object ThreadUtils {
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

    fun runMain(run: Runnable) {
        if (isMainThread()) {
            run.run()
        } else {
            Handler(Looper.getMainLooper()).post { run.run() }
        }
    }
}