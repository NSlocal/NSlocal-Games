package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class RAMOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_ram, null)
    private val tv: TextView = view.findViewById(R.id.tv_ram)
    fun update(percent: Int) { tv.text = "RAM: $percent%" }
}
