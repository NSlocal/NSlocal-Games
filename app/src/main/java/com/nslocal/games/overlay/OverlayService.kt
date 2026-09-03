package com.nslocal.games.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.nslocal.games.R
import com.nslocal.games.perf.PerformanceHelper

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var perf: PerformanceHelper
    private lateinit var fpsOverlay: FPSOverlay
    private lateinit var cpuOverlay: CPUOverlay
    private lateinit var batteryOverlay: BatteryOverlay
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            fpsOverlay.update()
            cpuOverlay.update()
            batteryOverlay.update()
            handler.postDelayed(this, 500)
        }
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        perf = PerformanceHelper(this)
        perf.startMonitoring()

        fpsOverlay = FPSOverlay(this, windowManager, perf)
        cpuOverlay = CPUOverlay(this, windowManager, perf)
        batteryOverlay = BatteryOverlay(this, windowManager, perf)

        fpsOverlay.create()
        cpuOverlay.create()
        batteryOverlay.create()

        handler.post(updateRunnable)
        startForeground(NOTIF_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
        perf.stopMonitoring()
        fpsOverlay.destroy()
        cpuOverlay.destroy()
        batteryOverlay.destroy()
    }

    private fun createNotification(): Notification {
        val chanId = "overlay_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(chanId, "NSlocal Games Overlay", NotificationManager.IMPORTANCE_LOW).let {
                getSystemService(NotificationManager::class.java).createNotificationChannel(it)
            }
        }
        return NotificationCompat.Builder(this, chanId)
            .setContentTitle("NSlocal Games")
            .setContentText("Overlay running — FPS/CPU/Battery monitor active")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null
    companion object { const val NOTIF_ID = 12345 }
}
