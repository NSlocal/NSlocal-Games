package com.nslocal.games.game

data class GameItem(
    val name: String,
    val packageName: String,
    val description: String = ""
)

object GameList {
    val games = listOf(
        GameItem(
            name = "QQ飞车",
            packageName = "com.tencent.tmgp.speedmobile",
            description = "QQ Speed Mobile — Tencent racing game"
        ),
        GameItem(
            name = "Speed Drifters",
            packageName = "com.garena.game.fctw",
            description = "Garena Speed Drifters racing game"
        )
    )
}
