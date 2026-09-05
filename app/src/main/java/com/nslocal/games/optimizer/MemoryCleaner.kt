package com.nslocal.games.optimizer

import android.app.ActivityManager
import android.content.Context
import android.os.Debug

class MemoryCleaner(private val ctx: Context) {
    private val am = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    fun cleanNow(): String {
        val before = getFreeRAM()
        System.gc()
        Runtime.getRuntime().gc()
        Debug.getMemoryInfo(ActivityManager.MemoryInfo().apply { am.getMemoryInfo(this) })
        val after = getFreeRAM()
        return "✅ Cleaned! Freed ~${((after - before)/1024/1024).toInt()} MB"
    }

    private fun getFreeRAM(): Long {
        val mi = ActivityManager.MemoryInfo(); am.getMemoryInfo(mi)
        return mi.availMem
    }

    fun autoCleanThreshold(percent: Int = 85): Boolean {
        val mi = ActivityManager.MemoryInfo(); am.getMemoryInfo(mi)
        val usedPercent = ((mi.totalMem - mi.availMem)*100 / mi.totalMem).toInt()
        if(usedPercent >= percent) { cleanNow(); return true }
        return false
    }
}
