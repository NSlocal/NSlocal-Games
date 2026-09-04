package com.nslocal.games.ui.glass

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent
import androidx.core.graphics.withPaint
import kotlin.math.*

class LiquidGlassView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        isDither = true
    }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#50FFFFFF")
        strokeWidth = 1.5f
    }
    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#40FFFFFF")
        isAntiAlias = true
    }

    private var touchX = -1000f
    private var touchY = -1000f
    private var pressed = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val r = minOf(w, h) / 2.2f

        val glassGradient = RadialGradient(
            w/2, h/2, max(w,h)/1.5f,
            intArrayOf(Color.parseColor("#DDFFFFFF"), Color.parseColor("#88FFFFFF"), Color.parseColor("#20FFFFFF")),
            floatArrayOf(0f, 0.6f, 1f), Shader.TileMode.CLAMP
        )
        paint.shader = glassGradient
        canvas.drawRoundRect(RectF(4f,4f,w-4f,h-4f), r, r, paint)
        canvas.drawRoundRect(RectF(4f,4f,w-4f,h-4f), r, r, borderPaint)

        val dist = sqrt((touchX - w/2).pow(2) + (touchY - h/2).pow(2))
        val maxDist = max(w,h)/2
        if (dist < maxDist * 0.85f) {
            val highlightR = r * 0.35f * (1f - dist/maxDist)
            canvas.drawCircle(touchX, touchY, highlightR, highlightPaint)
        } else {
            canvas.drawCircle(w * 0.3f, h * 0.25f, r * 0.3f, highlightPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                touchX = event.x
                touchY = event.y
                pressed = true
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchX = -1000f
                touchY = -1000f
                pressed = false
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }
}
