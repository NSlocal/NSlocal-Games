package com.nslocal.games.perf

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper

class PerformanceHelper(private val context: Context) {

    private val handler = Handler(Looper.getMainLooper())
    private var lastTimeNano = System.nanoTime()
    private var frameCount = 0
    var currentFPS = 0
        private set
    var currentCPU = 0
        private set
    var currentBattery = 0
        private set
    var isBatteryHealthy = true
        private set

    private val updateRunnable = object : Runnable {
        override fun run() {
            calculateFPS()
            updateBattery()
            updateCPU()
            handler.postDelayed(this, 500)
        }
    }

    fun startMonitoring() {
        handler.post(updateRunnable)
    }

    fun stopMonitoring() {
        handler.removeCallbacks(updateRunnable)
    }

    private fun calculateFPS() {
        frameCount++
        val now = System.nanoTime()
        val elapsed = (now - lastTimeNano) / 1_000_000_000.0
        if (elapsed >= 1.0) {
            currentFPS = (frameCount / elapsed).toInt().coerceIn(0, 240)
            frameCount = 0
            lastTimeNano = now
        }
    }

    private fun updateBattery() {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        currentBattery = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        isBatteryHealthy = currentBattery in 20..100
    }

    private fun updateCPU() {
        currentCPU = try {
            val load = Runtime.getRuntime().availableProcessors()
            (Math.random() * 60 + 20).toInt() // Simulated
        } catch (e: Exception) { 0 }
    }

    val isQualcomm: Boolean
        get() = Build.SOC_NAME.contains("qcom", ignoreCase = true) ||
                Build.BOARD.contains("qcom", ignoreCase = true) ||
                Build.MANUFACTURER.equals("Xiaomi|POCO|Redmi|OnePlus|Realme|iQOO", true)

    val isMediaTek: Boolean
        get() = Build.SOC_NAME.contains("mt", ignoreCase = true) ||
                Build.SOC_NAME.contains("mediatek", ignoreCase = true) ||
                Build.BOARD.contains("mt", ignoreCase = true)
}
