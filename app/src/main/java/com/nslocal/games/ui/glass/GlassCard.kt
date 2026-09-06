package com.nslocal.games.ui.glass

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.nslocal.games.R

class GlassCard @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(ctx, attrs, defStyle) {
    init {
        setBackgroundResource(R.drawable.glass_panel)
        setPadding(24, 16, 24, 16)
    }
}
