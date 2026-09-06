package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class CPUOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_cpu, null)
    private val tv: TextView = view.findViewById(R.id.currentCPU)
    fun update(percent: Int) { tv.text = "CPU: $percent%" }
}
