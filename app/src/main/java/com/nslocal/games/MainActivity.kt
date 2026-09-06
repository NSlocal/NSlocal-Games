package com.nslocal.games
import android.os.Bundle
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.tvDeviceInfo)
        val btn: Button = findViewById(R.id.btnStart)
        tv.text = "📱 ${Build.MANUFACTURER} ${Build.MODEL}\n📊 API: ${Build.VERSION.SDK_INT}"
        btn.setOnClickListener { tv.append("\n✅ Ready!") }
    }
}
