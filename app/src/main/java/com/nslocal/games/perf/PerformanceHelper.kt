package com.nslocal.games.perf
import android.content.Context; import android.os.Build

class PerformanceHelper(private val ctx: Context) {
    val socName: String get() = Build.HARDWARE
    val isQualcomm: Boolean get() = socName.contains("qcom", true) || socName.contains("sdm", true) || socName.contains("sm", true)
    val isMediaTek: Boolean get() = socName.contains("mt", true) || socName.contains("mediatek", true) || socName.contains("mtk", true)
    val deviceModel: String get() = Build.MODEL
    val androidVersion: String get() = Build.VERSION.RELEASE
    val sdkLevel: Int get() = Build.VERSION.SDK_INT
}
