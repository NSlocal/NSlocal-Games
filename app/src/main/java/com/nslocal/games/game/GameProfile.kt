package com.nslocal.games.game

data class GameProfile(
    val packageName: String,
    val name: String,
    val targetFPS: Int = 60,
    val gpuBoost: Boolean = true,
    val ramClean: Boolean = true,
    val touchOpt: Boolean = true,
    val dnsOpt: Boolean = true,
    val antiLag: Boolean = true,
    val antiFreeze: Boolean = true,
    val notificationBlock: Boolean = true,
    val brightnessLock: Boolean = false,
    val customResolution: String = ""
)
