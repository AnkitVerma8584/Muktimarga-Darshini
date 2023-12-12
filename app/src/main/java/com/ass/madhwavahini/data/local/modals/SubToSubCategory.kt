package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_to_sub_category")
data class SubToSubCategory(
    @PrimaryKey val id: Int,
    val catId: Int,
    val subCatId: Int,
    val name: String,
    val description: String
)