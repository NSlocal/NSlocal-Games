package com.nslocal.games.optimizer
import android.content.Context; import com.nslocal.games.perf.PerformanceHelper; import com.nslocal.games.perf.ThermalCoolingManager

class BoostEngine(private val ctx: Context) {
    private val perfHelper = PerformanceHelper(ctx)
    val thermalManager = ThermalCoolingManager(ctx)
    fun applyAll() { if (perfHelper.isQualcomm){}; if (perfHelper.isMediaTek){}; thermalManager.startMonitoring() }
    fun releaseAll() { thermalManager.stopMonitoring() }
}
