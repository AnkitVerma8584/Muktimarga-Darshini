package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName

data class HomePanchanga(
    val id: Int,
    val date: String,
    val week: String,
    val suryodaya: String,
    val suryasthamaya: String,
    @SerializedName("samvatsara")
    val title: String,
    val ayana: String,
    val ruthu: String,
    val masa: String,
    @SerializedName("masa_niyamaka")
    val masaNiyamaka: String,
    val paksha: String,
    val tithi: String,
    val vasara: String,
    val nakshatra: String,
    val yoga: String,
    val karana: String,
    @SerializedName("today_special")
    val todaySpecial: String
)
