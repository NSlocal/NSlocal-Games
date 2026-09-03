package com.nslocal.games.ui.glass

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withPaint

class LiquidGlassView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        isDither = true
    }
    private val glassGradient = RadialGradient(
        0.5f, 0.5f, 1.2f,
        intArrayOf(Color.parseColor("#EEFFFFFF"), Color.parseColor("#88FFFFFF"), Color.parseColor("#22FFFFFF")),
        floatArrayOf(0f, 0.5f, 1f),
        Shader.TileMode.CLAMP
    )
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#40FFFFFF")
        strokeWidth = 1.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val radius = (w.coerceAtMost(h)) / 2.2f

        paint.shader = glassGradient
        canvas.drawRoundRect(RectF(8f, 8f, w - 8f, h - 8f), radius, radius, paint)
        canvas.drawRoundRect(RectF(8f, 8f, w - 8f, h - 8f), radius, radius, borderPaint)

        canvas.drawCircle(w * 0.3f, h * 0.3f, radius * 0.35f, Paint().apply {
            color = Color.parseColor("#66FFFFFF")
            isAntiAlias = true
        })
    }
}
