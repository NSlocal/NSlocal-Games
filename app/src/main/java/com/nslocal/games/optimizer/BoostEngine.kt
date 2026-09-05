package com.nslocal.games.optimizer

import android.content.Context
import android.os.*
import com.nslocal.games.perf.*

class BoostEngine(private val ctx: Context) {
    private val touch = TouchResponder(ctx)
    private val net = NetworkOptimizer(ctx)
    private val ram = RAMHelper(ctx)
    private val perf = PerformanceHelper(ctx)

    fun boostAll() {
        ram.clean()
        touch.maxResponsive()
        net.lowLatency()
        System.gc()
    }

    fun boostForGame(pkg: String) {
        ram.clean()
        touch.optimize()
        net.optimizeDNS()
    }

    val deviceInfo: String get() = """
        📱 ${Build.MANUFACTURER} ${Build.MODEL}
        🧩 SoC: ${Build.SOC_NAME}
        📊 API: ${Build.VERSION.SDK_INT}
        ${if(perf.isQualcomm) "✅ Qualcomm Snapdragon" else ""}
        ${if(perf.isMediaTek) "✅ MediaTek" else ""}
    """.trimIndent()
}
