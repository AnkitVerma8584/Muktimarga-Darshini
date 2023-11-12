package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName

data class HomeGod(
    val id: Int,
    @SerializedName("god_name")
    val godName: String,
    val idSelected: Boolean = false,
)
