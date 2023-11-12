package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName

data class HomeSubCategory(
    val id: Int,
    @SerializedName("cat_id")
    val catId: Int,
    val name: String,
    val description: String,
    val image: String
)
