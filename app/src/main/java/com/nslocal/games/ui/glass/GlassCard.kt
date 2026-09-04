package com.nslocal.games.ui.glass

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

class GlassCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        shader = RadialGradient(0.5f,0.5f,1.2f,
            intArrayOf(0xE6FFFFFF.toInt(), 0x99FFFFFF.toInt(), 0x33FFFFFF.toInt()),
            floatArrayOf(0f,0.5f,1f), Shader.TileMode.CLAMP)
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = 0x40FFFFFF.toInt()
        strokeWidth = 1f
    }

    override fun dispatchDraw(canvas: Canvas) {
        val r = 28f * resources.displayMetrics.density
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, r, r, bgPaint)
        canvas.drawRoundRect(rect, r, r, strokePaint)
        super.dispatchDraw(canvas)
    }
}
