package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_category")
class SubCategory(
    @PrimaryKey val id: Int,
    val catId: Int,
    val name: String,
    val description: String,
    val image: String
)