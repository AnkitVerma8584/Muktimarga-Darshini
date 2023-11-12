package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner")
data class Banner(@PrimaryKey val image: String)
