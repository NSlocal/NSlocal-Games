package com.nslocal.games.perf

import android.content.Context
import android.os.Build
import android.provider.Settings

class TouchResponder(private val ctx: Context) {
    fun optimize() {
        if(Build.VERSION.SDK_INT >= 17) {
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 0.7f)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 0.7f)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 0.7f)
        }
    }
    fun maxResponsive() {
        if(Build.VERSION.SDK_INT >= 17) {
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 0.4f)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 0.4f)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 0.4f)
        }
    }
}
