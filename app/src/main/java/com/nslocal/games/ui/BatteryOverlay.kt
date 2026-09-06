package com.nslocal.games.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class BatteryOverlay(private val ctx: Context) : TextView(ctx), View.OnTouchListener {
    private var batteryPercent = 100
    private var initialX = 0f
    private var initialY = 0f
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val params = WindowManager.LayoutParams(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        android.graphics.PixelFormat.TRANSLUCENT
    ).apply {
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.TOP or Gravity.START
        x = 20
        y = 110
    }

    init {
        setTextColor(Color.parseColor("#F44336"))
        textSize = 14f
        setBackgroundColor(Color.parseColor("#CC000000"))
        setPadding(12, 6, 12, 6)
        setOnTouchListener(this)
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                updateBattery()
                handler.postDelayed(this, 2000)
            }
        })
    }

    private fun updateBattery() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val battery = ctx.registerReceiver(null, filter) ?: return
        val level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        batteryPercent = (level * 100) / scale
        text = "Battery: $batteryPercent%"
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x.toFloat()
                initialY = params.y.toFloat()
                initialTouchX = event.rawX
                initialTouchY = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                params.x = (initialX + (event.rawX - initialTouchX)).toInt()
                params.y = (initialY + (event.rawY - initialTouchY)).toInt()
                wm.updateViewLayout(this, params)
                return true
            }
        }
        return false
    }
}
