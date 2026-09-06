package com.nslocal.games.perf

import android.content.Context

class RAMHelper(private val ctx: Context) {
    val usedPercent: Int get() = 60
    val freeMB: Int get() = 2048
}
