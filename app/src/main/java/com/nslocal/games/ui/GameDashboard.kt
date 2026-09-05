package com.nslocal.games.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import com.nslocal.games.game.GameList
import com.nslocal.games.game.GameLauncher

class GameDashboard @JvmOverloads constructor(ctx: Context, a:AttributeSet?=null) : LinearLayout(ctx,a) {
    private val launcher = GameLauncher(ctx)
    init {
        orientation = VERTICAL; setPadding(24,24,24,24)
        val title = TextView(ctx).apply { text="🎮 Game Dashboard"; textSize=20f; setTextColor(Color.WHITE); setPadding(0,0,0,16) }
        addView(title)
        GameList.games.forEach { game ->
            val btn = GlassButton(ctx).apply {
                text = "▶ ${game.name}"
                setOnClickListener { launcher.launch(game.packageName) }
                setPadding(24,16,24,16)
            }
            addView(btn, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply { setMargins(0,8,0,8) })
        }
    }
}
