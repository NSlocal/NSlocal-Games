package com.nslocal.games

import android.os.Bundle
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.game.GameList

class MainActivity : AppCompatActivity() {
    private lateinit var tvDeviceInfo: TextView
    private lateinit var tvGameList: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDeviceInfo = findViewById(R.id.tvDeviceInfo)
        tvGameList = findViewById(R.id.tvGameList)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        tvDeviceInfo.text = """
            📱 ${Build.MANUFACTURER} ${Build.MODEL}
            📊 API Level: ${Build.VERSION.SDK_INT}
            🧩 NSlocal-Games v1.0.0
        """.trimIndent()

        val gameNames = GameList.games.joinToString("\n") { "• ${it.name} (${it.packageName})" }
        tvGameList.text = gameNames

        btnStart.setOnClickListener {
            tvDeviceInfo.append("\n✅ Overlay Starting...")
        }
        btnStop.setOnClickListener {
            tvDeviceInfo.append("\n⏹ Overlay Stopped")
        }
    }
}
