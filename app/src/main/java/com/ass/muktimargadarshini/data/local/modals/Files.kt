package com.ass.muktimargadarshini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class Files(
    @PrimaryKey val id: Int,
    val catId: Int,
    val subCatId: Int,
    val subToSubCatId: Int?,
    val name: String,
    val description: String,
    val fileUrl: String,
    val authorId: Int,
    val godId: Int
)