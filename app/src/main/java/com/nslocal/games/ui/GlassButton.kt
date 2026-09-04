package com.nslocal.games.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton

class GlassButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = RadialGradient(0.5f,0.5f,1f,
            intArrayOf(0xCCFFFFFF.toInt(), 0x66FFFFFF.toInt()),
            floatArrayOf(0f,1f), Shader.TileMode.CLAMP)
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = 0x30FFFFFF.toInt()
        strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas) {
        val r = height / 2f
        canvas.drawRoundRect(RectF(0f,0f,width.toFloat(),height.toFloat()), r, r, bgPaint)
        canvas.drawRoundRect(RectF(0f,0f,width.toFloat(),height.toFloat()), r, r, strokePaint)
        setTextColor(Color.WHITE)
        super.onDraw(canvas)
    }
}
