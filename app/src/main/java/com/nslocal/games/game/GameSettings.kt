package com.nslocal.games.game

import android.content.Context
import android.content.SharedPreferences

class GameSettings(ctx: Context) {
    private val prefs: SharedPreferences = ctx.getSharedPreferences("game_profiles", Context.MODE_PRIVATE)

    fun saveProfile(p: GameProfile) = prefs.edit()
        .putInt("${p.packageName}_fps", p.targetFPS)
        .putBoolean("${p.packageName}_gpu", p.gpuBoost)
        .putBoolean("${p.packageName}_ram", p.ramClean)
        .putBoolean("${p.packageName}_touch", p.touchOpt)
        .putBoolean("${p.packageName}_dns", p.dnsOpt)
        .putBoolean("${p.packageName}_antilag", p.antiLag)
        .putBoolean("${p.packageName}_freeze", p.antiFreeze)
        .apply()

    fun getProfile(pkg: String): GameProfile {
        val name = GameList.games.find{it.packageName==pkg}?.name ?: pkg
        return GameProfile(
            packageName = pkg, name = name,
            targetFPS = prefs.getInt("${pkg}_fps", 60),
            gpuBoost = prefs.getBoolean("${pkg}_gpu", true),
            ramClean = prefs.getBoolean("${pkg}_ram", true),
            touchOpt = prefs.getBoolean("${pkg}_touch", true),
            dnsOpt = prefs.getBoolean("${pkg}_dns", true),
            antiLag = prefs.getBoolean("${pkg}_antilag", true),
            antiFreeze = prefs.getBoolean("${pkg}_freeze", true)
        )
    }
}
