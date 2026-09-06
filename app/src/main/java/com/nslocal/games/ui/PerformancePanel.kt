package com.nslocal.games.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.nslocal.games.R
import com.nslocal.games.perf.PerformanceHelper
import com.nslocal.games.ui.glass.GlassCard

class PerformancePanel @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(ctx, attrs, defStyle) {

    private val glassCard: GlassCard
    private val tvInfo: TextView
    private val perfHelper = PerformanceHelper(ctx)

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        glassCard = GlassCard(ctx)
        val cardParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        glassCard.layoutParams = cardParams

        tvInfo = TextView(ctx)
        tvInfo.setTextColor(android.graphics.Color.WHITE)
        tvInfo.textSize = 12f
        tvInfo.setPadding(8, 8, 8, 8)

        glassCard.addView(tvInfo)
        addView(glassCard)

        updateInfo()
    }

    fun updateInfo() {
        tvInfo.text = """
            SOC: ${perfHelper.socName}
            Chip: ${if (perfHelper.isQualcomm) "Qualcomm" else if (perfHelper.isMediaTek) "MediaTek" else "Unknown"}
            Device: ${perfHelper.deviceModel}
            Android: ${perfHelper.androidVersion} (SDK ${perfHelper.sdkLevel})
        """.trimIndent()
    }
}
