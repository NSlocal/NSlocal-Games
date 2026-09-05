package com.nslocal.games.optimizer

import android.content.Context
import android.os.Build
import android.provider.Settings

class NoRootTweaks(private val ctx: Context) {
    fun applyAll() {
        animScale(0.5f)
        dnsGoogle()
    }

    fun animScale(scale: Float) {
        if(Build.VERSION.SDK_INT >= 17) {
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, scale)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, scale)
            Settings.Global.putFloat(ctx.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, scale)
        }
    }

    fun dnsGoogle() {
        if(Build.VERSION.SDK_INT >= 29) {
            Settings.Global.putString(ctx.contentResolver, "private_dns_specifier", "dns.google")
            Settings.Global.putInt(ctx.contentResolver, "private_dns_mode", 1)
        }
    }

    fun restoreDefault() { animScale(1f) }
}
