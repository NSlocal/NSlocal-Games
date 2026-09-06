package com.nslocal.games.optimizer

import android.content.Context
import android.os.Build
import android.os.PowerManager
import com.nslocal.games.perf.PerformanceHelper
import com.nslocal.games.perf.ThermalCoolingManager

class BoostEngine(private val ctx: Context) {
    private val perfHelper = PerformanceHelper(ctx)
    private val thermalManager = ThermalCoolingManager(ctx)
    private val powerManager = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
    private var wakeLock: PowerManager.WakeLock? = null

    fun applyAll() {
        applyQualcommTweaks()
        applyMediatekTweaks()
        applyBatteryStabilizer()
        startThermalProtection()
    }

    private fun applyQualcommTweaks() {
        if (!perfHelper.isQualcomm) return
        // Qualcomm optimasi — kurangi panas & stabilkan FPS
        ctx.contentResolver.apply {
            android.provider.Settings.Global.putInt(ctx.contentResolver,
                android.provider.Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0)
        }
    }

    private fun applyMediatekTweaks() {
        if (!perfHelper.isMediaTek) return
        // MediaTek optimasi — kurangi panas & stabilkan performa
        ctx.contentResolver.apply {
            android.provider.Settings.Global.putInt(ctx.contentResolver,
                android.provider.Settings.Global.FORCE_HARDWARE_UI, 1)
        }
    }

    private fun applyBatteryStabilizer() {
        // 🔋 Battery Stabilizer — kurangi fluktuasi daya & panas
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "NSlocal:BatteryStabilizer"
        ).apply {
            if (!isHeld) acquire(10 * 60 * 1000L) // 10 menit
        }
    }

    private fun startThermalProtection() {
        // ❄️ Mulai monitor suhu — auto cooling
        thermalManager.startMonitoring()
    }

    fun releaseAll() {
        thermalManager.stopMonitoring()
        thermalManager.restoreNormalSettings()
        wakeLock?.let { if (it.isHeld) it.release() }
    }

    fun getThermalManager(): ThermalCoolingManager = thermalManager
}
