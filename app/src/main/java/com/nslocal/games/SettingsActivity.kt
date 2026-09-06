package com.nslocal.games

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.perf.PerformanceHelper

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val perfHelper = PerformanceHelper(this)
        val deviceInfo = """
            Device: ${perfHelper.deviceModel}
            SOC: ${perfHelper.socName}
            Chip: ${if (perfHelper.isQualcomm) "Qualcomm" else if (perfHelper.isMediaTek) "MediaTek" else "Unknown"}
            Android: ${perfHelper.androidVersion} (SDK ${perfHelper.sdkLevel})
        """.trimIndent()
    }
}
