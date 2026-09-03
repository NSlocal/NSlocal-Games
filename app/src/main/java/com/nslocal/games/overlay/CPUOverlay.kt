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

class CPUOverlay(
    private val context: Context,
    private val windowManager: WindowManager,
    private val perf: PerformanceHelper
) {
    private var view: View? = null
    private var tvCPU: TextView? = null
    private var params: WindowManager.LayoutParams? = null
    private var offsetX = 0f
    private var offsetY = 0f

    fun create() {
        if (view != null) return
        view = LayoutInflater.from(context).inflate(R.layout.overlay_cpu, null)
        tvCPU = view!!.findViewById(R.id.tv_cpu)

        params = WindowManager.LayoutParams(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.TOP or Gravity.START
            x = 20
            y = 160
        }

        setupDrag()
        windowManager.addView(view, params)
    }

    fun update() {
        tvCPU?.text = "CPU: ${perf.currentCPU}%"
        tvCPU?.setTextColor(Color.parseColor("#FFFFFF")) // White
    }

    private fun setupDrag() {
        view?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    offsetX = event.rawX - params!!.x
                    offsetY = event.rawY - params!!.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params!!.x = (event.rawX - offsetX).toInt()
                    params!!.y = (event.rawY - offsetY).toInt()
                    windowManager.updateViewLayout(view, params)
                    true
                }
                else -> false
            }
        }
    }

    fun destroy() {
        view?.let { windowManager.removeView(it) }
        view = null
    }
}
