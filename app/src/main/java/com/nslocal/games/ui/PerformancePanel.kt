package com.nslocal.games.ui

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.*
import android.widget.*
import com.nslocal.games.R

class PerformancePanel(ctx: Context) : FrameLayout(ctx) {
    val card = GlassCard(ctx)
    val tvFPS = TextView(ctx); val tvCPU = TextView(ctx); val tvGPU = TextView(ctx)
    val tvRAM = TextView(ctx); val tvTEMP = TextView(ctx)

    init {
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        card.setPadding(24,20,24,20)
        addView(card, lp)

        val ll = LinearLayout(ctx); ll.orientation = LinearLayout.VERTICAL; ll.gravity = Gravity.CENTER
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { setMargins(0,4,0,4) }

        tvFPS.setTextColor(Color.parseColor("#4CAF50")); tvFPS.textSize=12f; tvFPS.setPadding(8,4,8,4); ll.addView(tvFPS,params)
        tvCPU.setTextColor(Color.WHITE); tvCPU.textSize=12f; ll.addView(tvCPU,params)
        tvGPU.setTextColor(Color.parseColor("#00CCFF")); tvGPU.textSize=12f; ll.addView(tvGPU,params)
        tvRAM.setTextColor(Color.parseColor("#CC00FF")); tvRAM.textSize=12f; ll.addView(tvRAM,params)
        tvTEMP.setTextColor(Color.parseColor("#FF9900")); tvTEMP.textSize=12f; ll.addView(tvTEMP,params)
        card.addView(ll, lp)
    }
}
