package com.nslocal.games.perf

import android.content.Context
import android.os.Build

class PerformanceHelper(private val ctx: Context) {
    val socName: String
        get() = if (Build.VERSION.SDK_INT >= 31) Build.SOC_NAME else Build.HARDWARE

    val isQualcomm: Boolean
        get() = socName.contains("qcom", ignoreCase = true) || socName.contains("sdm", ignoreCase = true) || socName.contains("sm", ignoreCase = true)

    val isMediatek: Boolean
        get() = socName.contains("mt", ignoreCase = true) || socName.contains("mediatek", ignoreCase = true) || socName.contains("mtk", ignoreCase = true)

    val deviceModel: String get() = Build.MODEL
    val androidVersion: String get() = Build.VERSION.RELEASE
    val sdkLevel: Int get() = Build.VERSION.SDK_INT
}
