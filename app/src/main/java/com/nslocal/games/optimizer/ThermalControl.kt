package com.nslocal.games.optimizer

import android.content.Context
import android.os.Build
import com.nslocal.games.perf.TempHelper

class ThermalControl(private val ctx: Context) {
    private val temp = TempHelper(ctx)
    var isThrottling = false; private set
    var tempC = 0; private set

    fun check(): Status {
        temp.update(); tempC = temp.tempC
        isThrottling = when {
            tempC >= 80 -> { Status.CRITICAL }
            tempC >= 68 -> { Status.WARNING }
            else -> Status.OK
        } != Status.OK
        return when {
            tempC >= 80 -> Status.CRITICAL
            tempC >= 68 -> Status.WARNING
            else -> Status.OK
        }
    }

    enum class Status { OK, WARNING, CRITICAL }
    val advice: String get() = when(check()) {
        Status.OK -> "✅ Temp OK — ${tempC}°C"
        Status.WARNING -> "⚠️ Hot — ${tempC}°C Reduce brightness"
        Status.CRITICAL -> "🔴 OVERHEAT — ${tempC}°C Close game & cool down!"
    }
}
