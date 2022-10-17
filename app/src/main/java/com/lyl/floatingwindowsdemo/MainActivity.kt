package com.lyl.floatingwindowsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lyl.floatingwindows.FloatConfig
import com.lyl.floatingwindows.FloatWindowFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FloatWindowFactory.init(application)
        for (i in -5..5) {
            var config = FloatConfig()
            config.x = (FloatWindowFactory.sDW / 2).toFloat()
            config.y = (i * 120).toFloat()
            FloatWindowFactory.sInstance.buildFloatWindow(config)
                .setFloatViewCreate(DemoFloatVIew()).showFloat(this, config)
        }
        for (i in -5..5) {
            var config = FloatConfig()
            config.onOtherApp = false
            config.x = 0f
            config.y = (i * 120f)
            FloatWindowFactory.sInstance.buildFloatWindow(config)
                .setFloatViewCreate(DemoFloatVIew()).showFloat(this, config)
        }
    }
}