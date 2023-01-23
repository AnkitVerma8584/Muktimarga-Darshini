package com.ass.muktimargadarshini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_to_sub_category")
data class SubToSubCategory(
    @PrimaryKey val id: Int,
    val cat_id: Int,
    val sub_cat_id: Int,
    val name: String,
    val description: String
)