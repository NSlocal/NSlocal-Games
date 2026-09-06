package com.nslocal.games.perf

import android.content.Context

class GPUHelper(private val ctx: Context) {
    val currentLoad: Int get() = 50
    fun boost() {}
}
