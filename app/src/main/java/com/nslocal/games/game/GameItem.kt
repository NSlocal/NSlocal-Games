package com.nslocal.games.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import android.os.Parcel

@Parcelize
data class GameItem(
    val name: String,
    val packageName: String
) : android.os.Parcelable
