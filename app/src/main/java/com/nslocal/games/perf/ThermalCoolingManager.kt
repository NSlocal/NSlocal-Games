package com.nslocal.games.perf

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager

class ThermalCoolingManager(private val ctx: Context) {
    private val handler = Handler(Looper.getMainLooper())
    var batteryTemp = 0f
        private set
    var cpuTemp = 0f
        private set
    var thermalStatus = 0
        private set
    private val powerManager = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager

    val TEMP_COOL = 35f
    val TEMP_WARM = 42f
    val TEMP_HOT = 48f
    val TEMP_CRITICAL = 52f

    var onTempChanged: ((Float, Float, Int) -> Unit)? = null
    var onCoolingAction: ((String, Float) -> Unit)? = null
    var isMonitoring = false
        private set

    private val tempRunnable = object : Runnable {
        override fun run() {
            readBatteryTemperature()
            readCpuTemperature()
            readThermalStatus()
            evaluateCoolingStrategy()
            handler.postDelayed(this, 1500)
        }
    }

    fun startMonitoring() {
        if (isMonitoring) return
        isMonitoring = true
        handler.post(tempRunnable)
    }

    fun stopMonitoring() {
        isMonitoring = false
        handler.removeCallbacks(tempRunnable)
    }

    private fun readBatteryTemperature() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val battery = ctx.registerReceiver(null, filter) ?: return
        batteryTemp = battery.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0f
    }

    private fun readCpuTemperature() {
        val paths = listOf(
            "/sys/devices/virtual/thermal/thermal_zone0/temp",
            "/sys/devices/virtual/thermal/thermal_zone1/temp",
            "/sys/class/thermal/thermal_zone0/temp"
        )
        for (path in paths) {
            try {
                val file = java.io.File(path)
                if (file.exists()) {
                    val value = file.readText().trim().toFloatOrNull() ?: continue
                    cpuTemp = if (value > 100) value / 1000f else value
                    if (cpuTemp in 20f..95f) break
                }
            } catch (e: Exception) {
                continue
            }
        }
        if (cpuTemp <= 0f) cpuTemp = batteryTemp + 5f
    }

    private fun readThermalStatus() {
        thermalStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            powerManager.currentThermalStatus
        } else {
            PowerManager.THERMAL_STATUS_NONE
        }
    }

    private fun evaluateCoolingStrategy() {
        val maxTemp = maxOf(batteryTemp, cpuTemp)
        onTempChanged?.invoke(batteryTemp, cpuTemp, thermalStatus)

        when {
            maxTemp < TEMP_WARM -> {
                onCoolingAction?.invoke("❄️ Normal — Full Performance", maxTemp)
            }
            maxTemp in TEMP_WARM..TEMP_HOT -> {
                onCoolingAction?.invoke("⚠️ Warm — Reduced Background Load", maxTemp)
            }
            maxTemp in TEMP_HOT..TEMP_CRITICAL -> {
                onCoolingAction?.invoke("🔥 HOT — Cooling Active! Reduce Graphics", maxTemp)
                applyCoolingLevel1()
            }
            maxTemp >= TEMP_CRITICAL -> {
                onCoolingAction?.invoke("❌ CRITICAL — Lower FPS + Effects OFF", maxTemp)
                applyCoolingLevel2()
            }
        }
    }

    private fun applyCoolingLevel1() {
        try {
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 0.5f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE, 0.5f
            )
        } catch (e: Exception) {}
    }

    private fun applyCoolingLevel2() {
        try {
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 0f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE, 0f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.WINDOW_ANIMATION_SCALE, 0f
            )
        } catch (e: Exception) {}
    }

    fun restoreNormalSettings() {
        try {
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 1f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE, 1f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.WINDOW_ANIMATION_SCALE, 1f
            )
        } catch (e: Exception) {}
    }

    fun getStatusText(): String = when {
        batteryTemp < TEMP_WARM -> "❄️ Cool"
        batteryTemp < TEMP_HOT -> "⚠️ Warm"
        batteryTemp < TEMP_CRITICAL -> "🔥 Hot"
        else -> "❌ Critical"
    }
}
