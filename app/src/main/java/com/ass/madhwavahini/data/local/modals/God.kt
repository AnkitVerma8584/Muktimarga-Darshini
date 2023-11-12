package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "god")
data class God(
    @PrimaryKey
    val id: Int,
    val godName: String
)
