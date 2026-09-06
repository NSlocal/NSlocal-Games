package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class GPUOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_gpu, null)
    private val tv: TextView = view.findViewById(R.id.tv_gpu)
    fun update(percent: Int) { tv.text = "GPU: $percent%" }
}
