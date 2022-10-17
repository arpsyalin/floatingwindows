package com.lyl.floatingwindows.interf

interface IAnimListener {
    fun notifyUpdate(isEnd: Boolean, changeX: Float, changeY: Float)
}