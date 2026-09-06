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

        val rootLayout = findViewById<LinearLayout>(android.R.id.content)
        val tvGame = TextView(this)
        tvGame.setTextColor(Color.WHITE)
        tvGame.textSize = 18f
        tvGame.text = "NSlocal Games — Dashboard"
        rootLayout?.addView(tvGame)
    }
}
