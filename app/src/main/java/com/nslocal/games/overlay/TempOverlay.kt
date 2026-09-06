package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class TempOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_temp, null)
    private val tv: TextView = view.findViewById(R.id.tv_temp)
    fun update(temp: Float) { tv.text = "TEMP: ${temp.toInt()}°C" }
}
