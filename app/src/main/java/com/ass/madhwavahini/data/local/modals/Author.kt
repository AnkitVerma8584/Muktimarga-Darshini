package com.ass.madhwavahini.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "author")
data class Author(
    @PrimaryKey
    val id: Int,
    val name: String
)
