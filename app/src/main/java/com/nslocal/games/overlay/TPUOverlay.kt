package com.nslocal.games.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.nslocal.games.R

class TPUOverlay(ctx: Context) {
    val view = LayoutInflater.from(ctx).inflate(R.layout.overlay_tpu, null)
    private val tv: TextView = view.findViewById(R.id.tv_tpu)
    fun update(percent: Int) { tv.text = "TPU: $percent%" }
}
