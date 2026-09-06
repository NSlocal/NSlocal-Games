package com.nslocal.games.game

data class GameItem(
    val name: String,
    val packageName: String
)

object GameList {
    val games = listOf(
        GameItem("QQ飞车 / QQ Speed", "com.tencent.tmgp.speedmobile"),
        GameItem("Speed Drifters", "com.tencent.tmgp.speedmobile"),
        GameItem("PUBG Mobile", "com.tencent.ig"),
        GameItem("Mobile Legends", "com.mobile.legends"),
        GameItem("Free Fire", "com.dts.freefireth"),
        GameItem("Call of Duty Mobile", "com.activision.callofduty.shooter"),
        GameItem("Honor of Kings", "com.tencent.tmgp.sgame"),
        GameItem("Arena of Valor", "com.ngame.allstar.eu"),
        GameItem("Garena RoV", "com.garena.game.kgth"),
        GameItem("eFootball", "jp.konami.pesam")
    )
}
