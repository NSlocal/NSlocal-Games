package com.nslocal.games.game

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class GameManager(private val context: Context) {

    fun isGameInstalled(packageName: String): Boolean = try {
        context.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    fun launchGame(packageName: String): Boolean {
        if (!isGameInstalled(packageName)) return false
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }

    fun getInstalledGames(): List<GameItem> =
        GameList.games.filter { isGameInstalled(it.packageName) }
}
