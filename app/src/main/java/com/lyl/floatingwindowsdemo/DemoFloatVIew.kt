package com.lyl.floatingwindowsdemo

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.lyl.floatingwindows.interf.IFloatViewCreate

class DemoFloatVIew : IFloatViewCreate {
    override fun createView(activity: Activity): View {
        return LayoutInflater.from(activity)
            .inflate(R.layout.float_robot, null)
    }

    override fun onHide() {


    }

    override fun onShow() {

    }
}