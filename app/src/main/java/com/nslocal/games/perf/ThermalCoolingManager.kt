package com.nslocal.games.perf
import android.content.Context; import android.content.Intent; import android.content.IntentFilter
import android.os.BatteryManager; import android.os.Handler; import android.os.Looper

class ThermalCoolingManager(private val ctx: Context) {
    private val handler = Handler(Looper.getMainLooper())
    var batteryTemp = 0f; private set
    var cpuTemp = 0f; private set
    val TEMP_WARM = 42f; val TEMP_HOT = 48f
    var onTempChanged: ((Float, Float) -> Unit)? = null
    var isMonitoring = false; private set

    private val tempRunnable = object : Runnable {
        override fun run() { readBatteryTemp(); readCpuTemp(); onTempChanged?.invoke(batteryTemp, cpuTemp); handler.postDelayed(this, 2000) }
    }

    fun startMonitoring() { if (isMonitoring) return; isMonitoring = true; handler.post(tempRunnable) }
    fun stopMonitoring() { isMonitoring = false; handler.removeCallbacks(tempRunnable) }

    private fun readBatteryTemp() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val battery = ctx.registerReceiver(null, filter) ?: return
        batteryTemp = battery.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0f
    }
    private fun readCpuTemp() { cpuTemp = batteryTemp + 5f }
}
