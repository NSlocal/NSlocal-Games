package com.nslocal.games.optimizer

import android.content.Context

class ThermalControl(private val ctx: Context) {
    val currentTemp: Float get() = 45f
    val isOverheat: Boolean get() = currentTemp > 60f
}
