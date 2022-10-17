package com.lyl.floatingwindows.interf

import android.app.Activity
import android.view.View
import com.lyl.floatingwindows.FloatConfig

interface IFloatWindow {
    fun showFloat(activity: Activity) {}
    fun showFloat(activity: Activity, config: FloatConfig) {}
    fun hideFlat()
    fun update()
    fun setFloatViewCreate(view: IFloatViewCreate): IFloatWindow
    fun canDrag(canDrag: Boolean)
    fun needAbsorb(needAbsorb: Boolean)
    fun setShowLocation(x: Int, y: Int) {

    }

}