package com.nslocal.games.ui.glass
import android.content.Context; import android.graphics.*; import android.util.AttributeSet; import android.view.View; import androidx.core.view.ViewCompat; import androidx.core.view.WindowInsetsCompat

class LiquidGlassView @JvmOverloads constructor(ctx: Context, attrs: AttributeSet?=null, defStyle:Int=0): View(ctx,attrs,defStyle) {
    private val paint=Paint(Paint.ANTI_ALIAS_FLAG).apply { style=Paint.Style.FILL; color=Color.parseColor("#CCFFFFFF"); alpha=180 }
    private val strokePaint=Paint(Paint.ANTI_ALIAS_FLAG).apply { style=Paint.Style.STROKE; color=Color.parseColor("#40FFFFFF"); strokeWidth=2f }
    private val roundRect=RectF()

    override fun onSizeChanged(w:Int,h:Int,oldw:Int,oldh:Int) {
        super.onSizeChanged(w,h,oldw,oldh); setLayerType(LAYER_TYPE_HARDWARE,null)
        ViewCompat.setOnApplyWindowInsetsListener(this){_,ins->
            val bars=ins.getInsets(WindowInsetsCompat.Type.systemBars()); setPadding(0,bars.top,0,bars.bottom); ins
        }
    }
    override fun onDraw(canvas:Canvas) {
        super.onDraw(canvas); roundRect.set(20f,20f,width-20f,height-20f)
        canvas.drawRoundRect(roundRect,24f,24f,paint); canvas.drawRoundRect(roundRect,24f,24f,strokePaint)
    }
}
