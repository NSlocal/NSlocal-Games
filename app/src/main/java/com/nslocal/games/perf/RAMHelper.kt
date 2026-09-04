package com.nslocal.games.perf

import android.app.ActivityManager
import android.content.Context

class RAMHelper(private val ctx: Context) {
    var usage = 0; private set
    var totalMB = 0L; private set
    var freeMB = 0L; private set
    private val am = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    fun update() {
        val memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
        totalMB = memInfo.totalMem / 1048576
        freeMB = memInfo.availMem / 1048576
        usage = ((totalMB - freeMB) * 100 / totalMB).toInt().coerceIn(0,100)
    }
    fun clean() { System.gc(); Runtime.getRuntime().gc() }
}
