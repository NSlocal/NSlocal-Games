package com.nslocal.games.optimizer

import android.content.Context
import android.os.Build
import com.nslocal.games.perf.PerformanceHelper

class BoostEngine(private val ctx: Context) {
    private val touch = TouchResponder(ctx)
    private val net = NetworkOptimizer(ctx)
    private val ram = MemoryCleaner(ctx)
    private val perf = PerformanceHelper()

    fun boostAll() {
        ram.cleanNow()
        touch.maxResponsive()
        net.lowLatency()
        System.gc()
    }

    fun boostForGame(pkg: String) {
        ram.cleanNow()
        touch.optimize()
        net.optimizeDNS()
    }

    val deviceInfo: String get() = """
        📱 ${Build.MANUFACTURER} ${Build.MODEL}
        🧩 SoC: ${perf.socName}
        📊 API: ${Build.VERSION.SDK_INT}
        ${if(perf.isQualcomm) "✅ Qualcomm Snapdragon" else ""}
        ${if(perf.isMediaTek) "✅ MediaTek" else ""}
    """.trimIndent()
}
