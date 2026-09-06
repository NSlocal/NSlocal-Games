package com.nslocal.games.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.R

class GameDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<LinearLayout>(R.id.root_layout)
        val tvGame = TextView(this)
        tvGame.setTextColor(Color.WHITE)
        tvGame.textSize = 18f

        val gameName = intent.getStringExtra("game_name")
        if (!gameName.isNullOrEmpty()) {
            tvGame.text = gameName
        } else {
            tvGame.text = "No Game Selected"
        }

        rootLayout?.addView(tvGame)
    }
}
