package com.nslocal.games.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class GlassButton @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatButton(ctx, attrs, defStyle) {
    init {
        setBackgroundResource(R.drawable.glass_bg)
        setTextColor(android.graphics.Color.WHITE)
    }
}
