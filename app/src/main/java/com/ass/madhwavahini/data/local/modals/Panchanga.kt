package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "panchanga")
data class Panchanga(
    @PrimaryKey
    val id: Int,
    val date: String,
    val week: String,
    val suryodaya: String,
    val suryasthamaya: String,
    val samvatsara: String,
    val ayana: String,
    val ruthu: String,
    val masa: String,
    val masaNiyamaka: String,
    val paksha: String,
    val tithi: String,
    val vasara: String,
    val nakshatra: String,
    val yoga: String,
    val karana: String,
    val todaySpecial: String
)