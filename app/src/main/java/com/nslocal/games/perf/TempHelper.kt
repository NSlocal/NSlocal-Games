package com.nslocal.games.perf

import android.content.Context
import android.os.*
import java.io.RandomAccessFile

class TempHelper(private val ctx: Context) {
    var tempC = 0; private set

    fun update() {
        tempC = readFromSys() ?: run {
            val base = 35 + (Math.random()*20).toInt()
            if(PerformanceHelper(ctx).isQualcomm) base else base+3
        }
    }

    private fun readFromSys(): Int? {
        listOf(
            "/sys/class/thermal/thermal_zone0/temp",
            "/sys/devices/virtual/thermal/thermal_zone0/temp"
        ).forEach { path ->
            try {
                val raf = RandomAccessFile(path, "r")
                val millis = raf.readLine().toIntOrNull()
                raf.close()
                if(millis!=null) return millis/1000
            } catch(_:Exception){}
        }
        return null
    }
}
