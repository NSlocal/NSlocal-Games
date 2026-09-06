package com.nslocal.games.optimizer

import android.content.Context
import com.nslocal.games.perf.PerformanceHelper
import com.nslocal.games.perf.ThermalCoolingManager

class BoostEngine(private val ctx: Context) {
    private val perfHelper = PerformanceHelper(ctx)
    val thermalManager = ThermalCoolingManager(ctx)

    fun applyAll() {
        if (perfHelper.isQualcomm) applyQualcomm()
        if (perfHelper.isMediaTek) applyMediatek()
        thermalManager.startMonitoring()
    }

    private fun applyQualcomm() {}
    private fun applyMediatek() {}

    fun releaseAll() {
        thermalManager.stopMonitoring()
    }
}
