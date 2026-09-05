package com.nslocal.games

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.nslocal.games.optimizer.NoRootTweaks

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED && ctx!=null) {
            NoRootTweaks(ctx).applyAll()
        }
    }
}
