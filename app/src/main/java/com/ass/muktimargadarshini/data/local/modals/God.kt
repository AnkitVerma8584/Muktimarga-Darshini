package com.ass.muktimargadarshini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "god")
data class God(
    @PrimaryKey
    val id: Int,
    val god_name: String
)
