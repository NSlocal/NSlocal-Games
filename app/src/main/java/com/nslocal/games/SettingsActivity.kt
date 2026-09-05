package com.nslocal.games

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.optimizer.BoostEngine
import com.nslocal.games.optimizer.MemoryCleaner

class SettingsActivity : AppCompatActivity() {
    private lateinit var btnBoost: Button
    private lateinit var btnCleanRAM: Button
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnBoost = findViewById(R.id.btnBoost)
        btnCleanRAM = findViewById(R.id.btnCleanRAM)
        tvStatus = findViewById(R.id.tvStatus)

        val boost = BoostEngine(this)
        val cleaner = MemoryCleaner(this)

        btnBoost.setOnClickListener {
            tvStatus.text = boost.deviceInfo + "\n✅ Boost Applied!"
        }
        btnCleanRAM.setOnClickListener {
            tvStatus.text = cleaner.cleanNow()
        }
    }
}
