package com.lyl.floatingwindows.interf

import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import com.lyl.floatingwindows.FloatConfig
import com.lyl.floatingwindows.FloatWindowFactory

interface IFloatViewCreate {
    fun createView(activity: Activity): View
    fun onHide()
    fun onShow()
    fun adsorbAnim(config: FloatConfig, width: Int, height: Int, iAnimListener: IAnimListener) {
        var center = (FloatWindowFactory.sDW / 2).toFloat()
        var target = center
        val valueAnimator: ValueAnimator =
            if (if (config.onOtherApp) {
                    config.x <= 0
                } else {
                    config.x <= center
                }
            ) {
                //向左贴边
                target = if (config.onOtherApp) -center else 0f
                ValueAnimator.ofFloat(config.x, target)
            } else {
                //向右贴边
                target =
                    if (config.onOtherApp) center else (FloatWindowFactory.sDW - width).toFloat()
                ValueAnimator.ofFloat(config.x, target)
            }


        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            var changeX = animation.animatedValue as Float
            iAnimListener.notifyUpdate(
                kotlin.math.abs(target) == kotlin.math.abs(changeX),
                changeX,
                config.y
            )
        }
        valueAnimator.duration = config.defaultAdsorbTime
        valueAnimator.start()
    }
}