package com.nslocal.games.optimizer

import android.content.Context
import android.os.PowerManager
import com.nslocal.games.perf.PerformanceHelper
import com.nslocal.games.perf.ThermalCoolingManager

class BoostEngine(private val ctx: Context) {
    private val perfHelper = PerformanceHelper(ctx)
    val thermalManager = ThermalCoolingManager(ctx)
    private val powerManager = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
    private var wakeLock: PowerManager.WakeLock? = null

    fun applyAll() {
        if (perfHelper.isQualcomm) applyQualcommTweaks()
        if (perfHelper.isMediaTek) applyMediatekTweaks()
        applyBatteryStabilizer()
        thermalManager.startMonitoring()
    }

    private fun applyQualcommTweaks() {}
    private fun applyMediatekTweaks() {}

    private fun applyBatteryStabilizer() {
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "NSlocal:BatteryStabilizer"
        )
        if (!wakeLock!!.isHeld) {
            wakeLock!!.acquire(10 * 60 * 1000L)
        }
    }

    fun releaseAll() {
        thermalManager.stopMonitoring()
        thermalManager.restoreNormalSettings()
        wakeLock?.let { if (it.isHeld) it.release() }
    }
}
