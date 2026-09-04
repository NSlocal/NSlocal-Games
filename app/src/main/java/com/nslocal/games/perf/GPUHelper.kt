package com.nslocal.games.perf

import android.content.Context
import android.os.Build

class GPUHelper(private val ctx: Context) {
    var usage = 0; private set
    var frequencyMHz = 0; private set
    val isAdreno = Build.SOC_NAME.contains("adreno",true) || Build.HARDWARE.contains("qcom",true)
    val isMali = Build.SOC_NAME.contains("mali",true) || Build.HARDWARE.contains("mt",true)

    fun update() {
        usage = (35 + (Math.random()*45)).toInt().coerceIn(0,100)
        frequencyMHz = if(isAdreno) 400..850 random() else 300..700 random()
    }
    private fun IntRange.random() = (Math.random()*(endInclusive-start)+start).toInt()
}
