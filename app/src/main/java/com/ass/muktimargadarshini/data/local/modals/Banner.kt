package com.ass.muktimargadarshini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner")
data class Banner(@PrimaryKey val image: String)
