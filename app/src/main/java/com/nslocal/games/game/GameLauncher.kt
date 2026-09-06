package com.nslocal.games.game

import android.content.Context
import android.content.Intent

class GameLauncher(private val ctx: Context) {
    fun launch(game: GameItem) {
        val intent = ctx.packageManager.getLaunchIntentForPackage(game.packageName)
        if (intent != null) ctx.startActivity(intent)
    }
}
