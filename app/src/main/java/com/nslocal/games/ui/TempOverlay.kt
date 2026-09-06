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
import com.nslocal.games.perf.ThermalCoolingManager

class TempOverlay(private val ctx: Context) : TextView(ctx), View.OnTouchListener {
    private var initialX = 0f; private var initialY = 0f
    private var initialTouchX = 0f; private var initialTouchY = 0f
    private val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val thermalManager = ThermalCoolingManager(ctx)

    val params = WindowManager.LayoutParams(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        android.graphics.PixelFormat.TRANSLUCENT
    ).apply {
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.TOP or Gravity.START
        x = 20; y = 155
    }

    init {
        textSize = 13f
        setBackgroundColor(Color.parseColor("#CC000000"))
        setPadding(14, 8, 14, 8)
        setOnTouchListener(this)

        thermalManager.onTempChanged = { battTemp, cpuTemp, _ ->
            updateTempDisplay(battTemp, cpuTemp)
        }
        thermalManager.onCoolingAction = { action, temp ->
            updateStatus(action, temp)
        }
        thermalManager.startMonitoring()
    }

    private fun updateTempDisplay(batt: Float, cpu: Float) {
        val tempColor = when {
            batt < 38f -> Color.parseColor("#00E676")  // Hijau — aman
            batt < 45f -> Color.parseColor("#FFC107")  // Kuning — waspada
            batt < 50f -> Color.parseColor("#FF9800")  // Oranye — panas
            else -> Color.parseColor("#F44336")        // Merah — bahaya
        }
        setTextColor(tempColor)
        text = "🔋 Bat: ${batt.formatTemp()}°C | 🔥 CPU: ${cpu.formatTemp()}°C"
    }

    private fun updateStatus(action: String, temp: Float) {
        // Bisa tambah indikator status di sini
    }

    private fun Float.formatTemp(): String = String.format("%.1f", this)

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x.toFloat(); initialY = params.y.toFloat()
                initialTouchX = event.rawX; initialTouchY = event.rawY
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

    fun destroy() {
        thermalManager.stopMonitoring()
        try { wm.removeView(this) } catch (_: Exception) {}
    }
}
