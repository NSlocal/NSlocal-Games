package com.nslocal.games.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.nslocal.games.R

class GlassButton @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatButton(ctx, attrs, defStyle) {
    init {
        setBackgroundResource(R.drawable.glass_bg)
        setTextColor(android.graphics.Color.WHITE)
        setPadding(32, 16, 32, 16)
        textSize = 14f
    }
}
