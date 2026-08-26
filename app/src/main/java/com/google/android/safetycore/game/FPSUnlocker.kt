package com.google.android.safetycore.game

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.WindowManager

class FPSUnlocker(private val context: Context) {

    companion object {
        private const val TAG = "FPSUnlocker"
        var targetFps: Int = 60
            private set
        var isUnlockEnabled: Boolean = true
    }

    fun setTargetFPS(packageName: String, fps: Int): Boolean {
        val config = GameList.getConfig(packageName) ?: run {
            Log.w(TAG, "Game tidak didukung: $packageName")
            return false
        }

        if (fps > config.maxSupportedFps) {
            Log.w(TAG, "FPS $fps melebihi batas ${config.maxSupportedFps} — dibatasi otomatis")
            targetFps = config.maxSupportedFps
        } else {
            targetFps = fps
        }

        when (config.unlockMethod) {
            UnlockMethod.SPOOF_DEVICE -> spoofDeviceForHighFPS()
            UnlockMethod.CONFIG_OVERRIDE -> overrideConfigFPS(packageName, targetFps)
            else -> Log.d(TAG, "Metode unlock standar: $targetFps FPS")
        }

        Log.i(TAG, "✅ $packageName → Target FPS: $targetFps")
        return true
    }

    private fun spoofDeviceForHighFPS() {
        // Simulasi spoof perangkat flagship untuk bypass whitelist FPS[[__LINK_ICON]](https://github.com/OneB1ank/zygisk-Tweaker/blob/main/README-zh.md?f_link_type=f_linkinlinenote&flow_extra=eyJpbmxpbmVfZGlzcGxheV9wb3NpdGlvbiI6MCwiZG9jX3Bvc2l0aW9uIjowLCJkb2NfaWQiOiJiNDVkZDg3N2U2NmMwODJhLTc5Y2U4NGVjOTA0MTJiODQifQ%3D%3D "[__LINK_ICON]")
        val flagshipDevice = mapOf(
            "MANUFACTURER" to "Samsung",
            "MODEL" to "SM-S928B", // S24 Ultra
            "DEVICE" to "dm3q",
            "BOARD" to "sm8650"
        )
        Log.d(TAG, "📡 Device spoof aktif → Flagship profile untuk unlock FPS")
        // Di implementasi asli: set system properties / hook Build class
    }

    private fun overrideConfigFPS(packageName: String, fps: Int) {
        // Override file config game untuk force FPS tinggi
        Log.d(TAG, "📝 Config override: $packageName → ${fps}FPS")
    }

    fun getCurrentRefreshRate(): Float {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val metrics = android.util.DisplayMetrics()
        display.getRealMetrics(metrics)
        return display.refreshRate
    }

    fun disableFPSUnlock() {
        isUnlockEnabled = false
        targetFps = 60
        Log.i(TAG, "🔒 FPS Unlock DINONAKTIFKAN → kembali ke 60 FPS standar")
    }

    fun enableFPSUnlock() {
        isUnlockEnabled = true
        Log.i(TAG, "🔓 FPS Unlock DIAKTIFKAN")
    }
}
