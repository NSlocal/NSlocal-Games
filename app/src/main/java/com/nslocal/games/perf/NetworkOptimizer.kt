package com.nslocal.games.perf

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings

class NetworkOptimizer(private val ctx: Context) {
    fun optimizeDNS() {
        if(Build.VERSION.SDK_INT >= 29) {
            Settings.Global.putString(ctx.contentResolver, "private_dns_specifier", "dns.google")
            Settings.Global.putInt(ctx.contentResolver, "private_dns_mode", 1)
        }
    }
    fun lowLatency() {
        optimizeDNS()
    }
}
