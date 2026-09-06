package com.nslocal.games.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.R
import com.nslocal.games.game.GameItem

class GameDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<LinearLayout>(R.id.root_layout)
        val tvGame = TextView(this)
        tvGame.setTextColor(Color.WHITE)
        tvGame.textSize = 18f

        val game: GameItem? = intent.getParcelableExtra("game")
        if (game != null) {
            tvGame.text = game.name
        } else {
            tvGame.text = "No Game Selected"
        }

        rootLayout?.addView(tvGame)
    }
}
