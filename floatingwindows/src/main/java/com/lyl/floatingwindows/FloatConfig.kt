package com.lyl.floatingwindows

import com.lyl.floatingwindows.interf.IFloatViewCreate

class FloatConfig {
    var onOtherApp = true
    var canDrag = true
    var needAbsorb = true
    var x = 0f
    var y = 0f
    var viewCreate: Class<IFloatViewCreate>? = null
    var defaultAdsorbTime = 300L
    //key相同库只记录一个缓存位置
    var key = ""
}