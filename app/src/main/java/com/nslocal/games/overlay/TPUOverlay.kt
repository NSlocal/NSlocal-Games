package com.nslocal.games.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.nslocal.games.R
import com.nslocal.games.perf.PerformanceHelper

class TPUOverlay(
    private val ctx: Context,
    private val wm: WindowManager,
    private val perf: PerformanceHelper
) {
    private var v: View? = null
    private var tv: TextView? = null
    private var lp: WindowManager.LayoutParams? = null
    private var ox=0f, oy=0f

    fun create() {
        if(v!=null) return
        v = LayoutInflater.from(ctx).inflate(R.layout.overlay_tpu, null)
        tv = v!!.findViewById(R.id.tv_tpu)
        lp = WindowManager.LayoutParams(
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            width=WindowManager.LayoutParams.WRAP_CONTENT
            height=WindowManager.LayoutParams.WRAP_CONTENT
            gravity=Gravity.TOP or Gravity.START
            x=20; y=280
        }
        v!!.setOnTouchListener { _, e ->
            when(e.action) {
                MotionEvent.ACTION_DOWN -> { ox=e.rawX-lp!!.x; oy=e.rawY-lp!!.y }
                MotionEvent.ACTION_MOVE -> { lp!!.x=(e.rawX-ox).toInt(); lp!!.y=(e.rawY-oy).toInt(); wm.updateViewLayout(v,lp) }
            }
            true
        }
        wm.addView(v,lp)
    }

    fun update() {
        tv?.text = "TPU: ${perf.currentCPU}%"
        tv?.setTextColor(Color.parseColor("#FFCC00"))
    }

    fun destroy() { v?.let{wm.removeView(it)}; v=null }
}
