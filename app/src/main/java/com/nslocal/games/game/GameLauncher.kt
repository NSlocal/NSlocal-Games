package com.nslocal.games.game

import android.content.Context
import android.content.Intent
import android.os.Build
import com.nslocal.games.perf.*

class GameLauncher(private val ctx: Context) {
    private val settings = GameSettings(ctx)
    private val touch = TouchResponder(ctx)
    private val net = NetworkOptimizer(ctx)
    private val ram = RAMHelper(ctx)

    fun launch(pkg: String): Boolean {
        val pm = ctx.packageManager
        val intent = pm.getLaunchIntentForPackage(pkg) ?: return false
        val profile = settings.getProfile(pkg)

        if(profile.ramClean) ram.clean()
        if(profile.touchOpt) touch.optimize()
        if(profile.dnsOpt) net.lowLatency()

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        ctx.startActivity(intent)
        return true
    }
}
