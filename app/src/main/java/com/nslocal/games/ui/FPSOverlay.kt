package com.nslocal.games.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import kotlin.math.roundToInt

class FPSOverlay(private val ctx: Context) : TextView(ctx), View.OnTouchListener {
    var fps = 60
    private var lastTime = System.nanoTime()
    private var frames = 0
    private var initialX = 0f; private var initialY = 0f
    private var initialTouchX = 0f; private var initialTouchY = 0f
    private val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val params = WindowManager.LayoutParams(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        android.graphics.PixelFormat.TRANSLUCENT
    ).apply { width = WindowManager.LayoutParams.WRAP_CONTENT; height = WindowManager.LayoutParams.WRAP_CONTENT; gravity = Gravity.TOP or Gravity.START; x = 20; y = 20 }

    init {
        setTextColor(Color.parseColor("#00E676")) // GREEN FPS
        textSize = 14f
        setBackgroundColor(Color.parseColor("#CC000000"))
        setPadding(12, 6, 12, 6)
        setOnTouchListener(this)
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                val now = System.nanoTime(); frames++
                if (now - lastTime >= 1_000_000_000) {
                    fps = (frames * 1_000_000_000f / (now - lastTime)).roundToInt()
                    text = "FPS: $fps"
                    frames = 0; lastTime = now
                }
                Handler(Looper.getMainLooper()).postDelayed(this, 50)
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> { initialX = params.x.toFloat(); initialY = params.y.toFloat(); initialTouchX = event.rawX; initialTouchY = event.rawY; return true }
            MotionEvent.ACTION_MOVE -> { params.x = (initialX + (event.rawX - initialTouchX)).toInt(); params.y = (initialY + (event.rawY - initialTouchY)).toInt(); wm.updateViewLayout(this, params); return true }
        }
        return false
    }
}
