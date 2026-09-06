package com.nslocal.games.perf

import android.content.Context

class TempHelper(private val ctx: Context) {
    val currentCelsius: Float get() = 45f
    val isWarning: Boolean get() = currentCelsius > 55f
}
