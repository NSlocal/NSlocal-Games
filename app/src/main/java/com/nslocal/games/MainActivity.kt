package com.nslocal.games

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.databinding.ActivityMainBinding
import com.nslocal.games.game.GameList
import com.nslocal.games.overlay.OverlayService
import com.nslocal.games.perf.PerformanceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var perf: PerformanceHelper
    private val REQUEST_OVERLAY = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        perf = PerformanceHelper(this)
        perf.startMonitoring()

        checkOverlayPermission()
        setupUI()
        loadGameList()
        showDeviceInfo()
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, REQUEST_OVERLAY)
        } else {
            startOverlayService()
        }
    }

    private fun setupUI() {
        binding.btnStart.setOnClickListener {
            if (Settings.canDrawOverlays(this)) {
                startOverlayService()
                Toast.makeText(this, "✅ Overlay Started!", Toast.LENGTH_SHORT).show()
            } else {
                checkOverlayPermission()
            }
        }
        binding.btnStop.setOnClickListener {
            stopService(Intent(this, OverlayService::class.java))
            Toast.makeText(this, "🛑 Overlay Stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadGameList() {
        val games = GameList.games
        val sb = StringBuilder()
        games.forEachIndexed { i, g ->
            sb.append("${i+1}. ${g.name}\n   └ ${g.packageName}\n")
        }
        binding.tvGameList.text = sb.toString()
    }

    private fun showDeviceInfo() {
        val chip = when {
            perf.isQualcomm -> "✅ Qualcomm Snapdragon"
            perf.isMediaTek -> "✅ MediaTek"
            else -> "⚠️ Generic"
        }
        binding.tvDeviceInfo.text = """
            📱 Device: ${Build.MANUFACTURER} ${Build.MODEL}
            🧩 Chip: $chip
            📊 API Level: ${Build.VERSION.SDK_INT}
            🎯 Target: Android 16+ (API 36)
        """.trimIndent()
    }

    private fun startOverlayService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, OverlayService::class.java))
        } else {
            startService(Intent(this, OverlayService::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        perf.stopMonitoring()
    }
}
