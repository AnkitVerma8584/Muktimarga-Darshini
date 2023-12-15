package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aradhna")
data class Aradhna(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val image: String
)
