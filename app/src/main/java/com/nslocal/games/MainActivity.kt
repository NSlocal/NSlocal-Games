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
    private lateinit var tvFps: TextView
    private lateinit var tvCpu: TextView
    private lateinit var tvBat: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvDeviceInfo: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnPerm: Button
    private var overlaysAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        glassView = LiquidGlassView(this)
        val rootView = findViewById<View>(android.R.id.content) as android.view.ViewGroup
        rootView.addView(glassView)

        tvDeviceInfo = findViewById(R.id.tvDeviceInfo)
        tvFps = findViewById(R.id.tvFps)
        tvCpu = findViewById(R.id.tvCpu)
        tvBat = findViewById(R.id.tvBat)
        tvTemp = findViewById(R.id.tvTemp)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnPerm = findViewById(R.id.btnPerm)

        tvDeviceInfo.text = "📱 ${Build.MANUFACTURER} ${Build.MODEL}\n📲 Android ${Build.VERSION.RELEASE} • SDK ${Build.VERSION.SDK_INT}"
        boostEngine = BoostEngine(this)

        btnPerm.setOnClickListener { checkOverlayPermission() }
        btnStart.setOnClickListener {
            if (checkOverlayPermission()) {
                activateAllFeatures()
                Toast.makeText(this, "✅ ALL FEATURES ACTIVE!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "⚠️ Allow Overlay First!", Toast.LENGTH_LONG).show()
            }
        }
        btnStop.setOnClickListener { stopAllFeatures() }
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
            fpsOverlay = FPSOverlay(this) { fps -> runOnUiThread { tvFps.text = "$fps" } }
            cpuOverlay = CPUOverlay(this) { cpu -> runOnUiThread { tvCpu.text = "$cpu%" } }
            batteryOverlay = BatteryOverlay(this) { bat -> runOnUiThread { tvBat.text = "$bat%" } }
            tempOverlay = TempOverlay(this) { batt, cpu -> runOnUiThread { tvTemp.text = "%.1f°".format(batt) } }
            wm.addView(fpsOverlay, fpsOverlay.params)
            wm.addView(cpuOverlay, cpuOverlay.params)
            wm.addView(batteryOverlay, batteryOverlay.params)
            wm.addView(tempOverlay, tempOverlay.params)
            overlaysAdded = true
        }
        glassView.invalidate()
        btnStart.isEnabled = false; btnPerm.isEnabled = false
    }

    private fun stopAllFeatures() {
        if (overlaysAdded) {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            try { wm.removeView(fpsOverlay); wm.removeView(cpuOverlay); wm.removeView(batteryOverlay); tempOverlay.destroy() } catch (_: Exception) {}
            boostEngine.releaseAll()
            overlaysAdded = false
            btnStart.isEnabled = true; btnPerm.isEnabled = true
            tvFps.text = "--"; tvCpu.text = "--"; tvBat.text = "--"; tvTemp.text = "--"
            Toast.makeText(this, "⏹️ STOPPED — All features closed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() { super.onDestroy(); if (overlaysAdded) { stopAllFeatures() } }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) Toast.makeText(this, "✅ Permission ALLOWED!", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "❌ DENIED!", Toast.LENGTH_SHORT).show()
        }
    }
}
