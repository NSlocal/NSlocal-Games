package com.nslocal.games

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.databinding.ActivitySettingsBinding
import com.nslocal.games.optimizer.BoostEngine
import com.nslocal.games.optimizer.MemoryCleaner

class SettingsActivity : AppCompatActivity() {
    private lateinit var b: ActivitySettingsBinding
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(b.root)

        val boost = BoostEngine(this)
        val cleaner = MemoryCleaner(this)

        b.btnBoost.setOnClickListener { b.tvStatus.text = boost.deviceInfo + "\n✅ Boost Applied!" }
        b.btnCleanRAM.setOnClickListener { b.tvStatus.text = cleaner.cleanNow() }
    }
}
