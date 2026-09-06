package com.nslocal.games.game

import android.content.Context

class GameManager(private val ctx: Context) {
    var currentGame: GameItem? = null
    fun launchGame(game: GameItem) { currentGame = game }
    fun stopGame() { currentGame = null }
}
