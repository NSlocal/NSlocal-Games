package com.nslocal.games.perf

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.os.Handler
import android.os.Looper
import com.nslocal.games.optimizer.BoostEngine

class ThermalCoolingManager(private val ctx: Context) {
    private val handler = Handler(Looper.getMainLooper())
    private var batteryTemp = 0f
    private var cpuTemp = 0f
    private var thermalStatus = PowerManager.THERMAL_STATUS_NONE
    private val powerManager = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
    private val boostEngine = BoostEngine(ctx)

    // THRESHOLD — SUHU AMAN / PANAS / BERBAHAYA
    val TEMP_COOL = 35f      // ✅ Normal — performa penuh
    val TEMP_WARM = 42f      // ⚠️ Mulai panas — kurangi beban
    val TEMP_HOT = 48f       // 🔥 Panas — aktif pendingin
    val TEMP_CRITICAL = 52f  // ❌ Berbahaya — turunkan FPS / kurangi grafis

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
            handler.postDelayed(this, 1500) // cek tiap 1.5 detik
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
        // Baca suhu CPU dari sysfs — non-root support
        val paths = listOf(
            "/sys/devices/virtual/thermal/thermal_zone0/temp",
            "/sys/devices/virtual/thermal/thermal_zone1/temp",
            "/sys/class/thermal/thermal_zone0/temp",
            "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp"
        )
        for (path in paths) {
            try {
                val file = java.io.File(path)
                if (file.exists()) {
                    val value = file.readText().trim().toFloat()
                    cpuTemp = if (value > 100) value / 1000f else value
                    if (cpuTemp in 20f..90f) break
                }
            } catch (e: Exception) { continue }
        }
        if (cpuTemp <= 0f) cpuTemp = batteryTemp + 5f // fallback
    }

    private fun readThermalStatus() {
        thermalStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            powerManager.currentThermalStatus
        } else PowerManager.THERMAL_STATUS_NONE
    }

    private fun evaluateCoolingStrategy() {
        val maxTemp = maxOf(batteryTemp, cpuTemp)
        onTempChanged?.invoke(batteryTemp, cpuTemp, thermalStatus)

        when {
            // ❄️ SUHU NORMAL — PERFORMA PENUH
            maxTemp < TEMP_WARM -> {
                onCoolingAction?.invoke("❄️ Normal — Full Performance", maxTemp)
                boostEngine.applyAll() // aktifkan semua optimasi
            }

            // ⚠️ MULAI PANAS — KURANGI BEBAN RINGAN
            maxTemp in TEMP_WARM..TEMP_HOT -> {
                onCoolingAction?.invoke("⚠️ Warm — Reduced Background Load", maxTemp)
                boostEngine.applyAll()
            }

            // 🔥 PANAS — AKTIFKAN PENDINGIN OTOMATIS
            maxTemp in TEMP_HOT..TEMP_CRITICAL -> {
                onCoolingAction?.invoke("🔥 HOT — Cooling Active! Reduce Graphics/FPS", maxTemp)
                applyCoolingLevel1()
            }

            // ❌ BERBAHAYA — TURUNKAN GRAFIS & FPS
            maxTemp >= TEMP_CRITICAL -> {
                onCoolingAction?.invoke("❌ CRITICAL — Lower FPS + Disable Effects", maxTemp)
                applyCoolingLevel2()
            }
        }
    }

    private fun applyCoolingLevel1() {
        // Kurangi beban untuk mendinginkan HP — non-root aman
        ctx.contentResolver.apply {
            // Turunkan animasi system → kurangi beban GPU/CPU
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,
                0.5f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE,
                0.5f
            )
        }
    }

    private fun applyCoolingLevel2() {
        // Pendingin maksimal — turunkan beban berat
        ctx.contentResolver.apply {
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,
                0f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE,
                0f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.WINDOW_ANIMATION_SCALE,
                0f
            )
        }
    }

    fun restoreNormalSettings() {
        ctx.contentResolver.apply {
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,
                1f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE,
                1f
            )
            android.provider.Settings.Global.putFloat(
                ctx.contentResolver,
                android.provider.Settings.Global.WINDOW_ANIMATION_SCALE,
                1f
            )
        }
    }

    fun getBatteryTempCelsius(): Float = batteryTemp
    fun getCpuTempCelsius(): Float = cpuTemp
    fun getThermalLevel(): Int = thermalStatus

    fun getTempStatusText(): String = when {
        batteryTemp < TEMP_WARM -> "❄️ Cool"
        batteryTemp < TEMP_HOT -> "⚠️ Warm"
        batteryTemp < TEMP_CRITICAL -> "🔥 Hot"
        else -> "❌ Critical"
    }
}
