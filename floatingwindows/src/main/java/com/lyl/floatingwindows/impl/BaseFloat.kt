package com.lyl.floatingwindows.impl

import android.app.Activity
import android.view.View
import com.lyl.floatingwindows.FloatConfig
import com.lyl.floatingwindows.interf.*
import com.lyl.floatingwindows.utils.ClassUtils
import com.lyl.floatingwindows.utils.ThreadUtils
import com.lyl.floatingwindows.view.MoveAndClickLayout

abstract class BaseFloat(var floatConfig: FloatConfig) : IFloatWindow, IDragFloatListener,
    IAnimListener {
    var mDisplayW: Int = 720
    protected var mViewCreate: IFloatViewCreate? = null
    protected var mChildView: View? = null
    protected var mView: MoveAndClickLayout? = null
    var mAdsorbListener: IAdsorbListener? = null
    var mCacheListener: ICacheListener? = null
    override fun setFloatViewCreate(view: IFloatViewCreate): IFloatWindow {
        mViewCreate = view
        return this
    }

    override fun hideFlat() {
        ThreadUtils.runMain { hide() }
    }

    override fun showFloat(activity: Activity, config: FloatConfig) {
        this.floatConfig = config
        showFloat(activity)
    }

    override fun showFloat(activity: Activity) {
        ThreadUtils.runMain {
            if (mView == null) {
                if (mViewCreate == null) {
                    mViewCreate = ClassUtils.instance(floatConfig.viewCreate)
                    if (mViewCreate == null) {
                        throw Exception("please call setFloatViewCreate fun, or config the FloatViewCreate class")
                    }
                }
                mChildView = mViewCreate?.createView(activity)
                show(activity)
            } else {
                reShow()
            }
        }
    }

    private fun reShow() {
        mView!!.visibility = View.VISIBLE
    }

    private fun hide() {
        mView?.visibility = View.GONE
    }

    override fun canDrag(canDrag: Boolean) {
        this.floatConfig.canDrag = canDrag
    }

    override fun needAbsorb(needAbsorb: Boolean) {
        this.floatConfig.needAbsorb = needAbsorb
        var canDrag = needAbsorb
        if (needAbsorb) {
            canDrag = true
        }
        canDrag(canDrag)
    }

    override fun notifyUpdate(isEnd: Boolean, changeX: Float, changeY: Float) {
        if (isEnd) {
            onAdsorb(changeX > 0)
        }
        floatConfig.x = changeX
        floatConfig.y = changeY
        update()
    }

    override fun onMoveUp() {
        if (this.floatConfig.needAbsorb) {
            mViewCreate?.adsorbAnim(floatConfig, mView!!.width, mView!!.height, this)
        }
    }

    private fun onAdsorb(right: Boolean) {
        mAdsorbListener?.adsorb(right)
        mCacheListener?.cache(floatConfig)
    }

    override fun onMoveChange(x: Float, y: Float) {
        if (this.floatConfig.canDrag) {
            moveChangeXY(x, y)
            if (this.floatConfig.needAbsorb) {
                mCacheListener?.cache(floatConfig)
            }
        }
    }

    abstract fun moveChangeXY(x: Float, y: Float)
    abstract fun show(activity: Activity)
}