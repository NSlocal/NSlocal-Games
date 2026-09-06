package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class FPSOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_fps, null)
    private val tv: TextView = view.findViewById(R.id.currentFPS)
    fun update(fps: Int) { tv.text = "FPS: $fps" }
}
