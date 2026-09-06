package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class BatteryOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_battery, null)
    private val tv: TextView = view.findViewById(R.id.currentBattery)
    fun update(percent: Int) { tv.text = "Battery: $percent%" }
}
