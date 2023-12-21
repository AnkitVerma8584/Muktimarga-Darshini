package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "panchanga")
data class Panchanga(
    @PrimaryKey
    val id: Int,
    val sunrise: String,
    val sunset: String
)