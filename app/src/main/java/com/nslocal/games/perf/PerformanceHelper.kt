package com.nslocal.games.perf

import android.os.Build

class PerformanceHelper {
    val isQualcomm: Boolean
        get() = Build.HARDWARE.contains("qcom", ignoreCase = true) ||
                Build.BOARD.contains("qcom", ignoreCase = true) ||
                Build.MANUFACTURER.contains("qualcomm", ignoreCase = true)

    val isMediaTek: Boolean
        get() = Build.HARDWARE.contains("mt", ignoreCase = true) ||
                Build.HARDWARE.contains("mtk", ignoreCase = true) ||
                Build.BOARD.contains("mt", ignoreCase = true)

    val socName: String
        get() = if (Build.VERSION.SDK_INT >= 31) Build.SOC_NAME else Build.HARDWARE
}
