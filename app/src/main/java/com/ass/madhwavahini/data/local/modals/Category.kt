package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(@PrimaryKey val id: Int, val name: String, val image: String)