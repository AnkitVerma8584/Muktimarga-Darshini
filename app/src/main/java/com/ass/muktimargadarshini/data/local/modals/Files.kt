package com.ass.muktimargadarshini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class Files(
    @PrimaryKey val id: Int,
    val cat_id: Int,
    val sub_cat_id: Int,
    val sub_to_sub_cat_id: Int?,
    val name: String,
    val description: String,
    val fileUrl: String,
    val author_id: Int,
    val god_id: Int
)