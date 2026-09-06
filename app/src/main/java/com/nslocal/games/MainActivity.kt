package com.nslocal.games

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nslocal.games.optimizer.BoostEngine
import com.nslocal.games.ui.BatteryOverlay
import com.nslocal.games.ui.CPUOverlay
import com.nslocal.games.ui.FPSOverlay
import com.nslocal.games.ui.TempOverlay
import com.nslocal.games.ui.glass.LiquidGlassView

class MainActivity : AppCompatActivity() {
    companion object { const val REQUEST_OVERLAY = 1001 }

    private lateinit var glassView: LiquidGlassView
    private lateinit var fpsOverlay: FPSOverlay
    private lateinit var cpuOverlay: CPUOverlay
    private lateinit var batteryOverlay: BatteryOverlay
    private lateinit var tempOverlay: TempOverlay
    private lateinit var boostEngine: BoostEngine
    private var overlaysAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        glassView = LiquidGlassView(this)
        val rootView = findViewById<View>(android.R.id.content) as android.view.ViewGroup
        rootView.addView(glassView)
        val tv: TextView = findViewById(R.id.tvDeviceInfo)
        val btn: Button = findViewById(R.id.btnStart)
        val btnPerm = Button(this)
        btnPerm.text = "Allow Overlay Permission"
        btnPerm.setTextColor(android.graphics.Color.WHITE)
        btnPerm.setBackgroundColor(android.graphics.Color.parseColor("#4CAF50"))
        val layout = findViewById<LinearLayout>(R.id.root_layout)
        layout.addView(btnPerm)
        tv.text = "Device: ${Build.MANUFACTURER} ${Build.MODEL}\nAPI: ${Build.VERSION.SDK_INT}"
        boostEngine = BoostEngine(this)
        btnPerm.setOnClickListener { checkOverlayPermission() }
        btn.setOnClickListener {
            if (checkOverlayPermission()) {
                activateAllFeatures()
                Toast.makeText(this, "✅ ALL FEATURES ACTIVE!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "⚠️ Allow Overlay First!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkOverlayPermission(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQUEST_OVERLAY); false
        } else true
    } else true

    private fun activateAllFeatures() {
        boostEngine.applyAll()
        if (!overlaysAdded) {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            fpsOverlay = FPSOverlay(this); cpuOverlay = CPUOverlay(this)
            batteryOverlay = BatteryOverlay(this); tempOverlay = TempOverlay(this)
            wm.addView(fpsOverlay, fpsOverlay.params); wm.addView(cpuOverlay, cpuOverlay.params)
            wm.addView(batteryOverlay, batteryOverlay.params); wm.addView(tempOverlay, tempOverlay.params)
            overlaysAdded = true
        }
        glassView.invalidate()
    }

    override fun onDestroy() { super.onDestroy(); if (overlaysAdded) { tempOverlay.destroy(); boostEngine.releaseAll() } }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) Toast.makeText(this, "✅ Permission ALLOWED!", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "❌ DENIED!", Toast.LENGTH_SHORT).show()
        }
    }
}
