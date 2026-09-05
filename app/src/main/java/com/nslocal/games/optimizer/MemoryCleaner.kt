package com.nslocal.games.optimizer

import android.app.ActivityManager
import android.content.Context

class MemoryCleaner(private val ctx: Context) {
    private val am = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    fun cleanNow(): String {
        val before = getFreeRAM()
        System.gc()
        Runtime.getRuntime().gc()
        val after = getFreeRAM()
        return "✅ Cleaned! Freed ~${((after - before) / (1024 * 1024)).toInt()} MB"
    }

    private fun getFreeRAM(): Long {
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return mi.availMem
    }

    fun autoCleanThreshold(percent: Int = 85): Boolean {
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        val total = mi.totalMem
        val avail = mi.availMem
        val usedPercent = ((total - avail) * 100 / total).toInt()
        if (usedPercent >= percent) {
            cleanNow()
            return true
        }
        return false
    }
}
