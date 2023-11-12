package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName

data class HomeSubToSubCategory(
    val id: Int,
    @SerializedName("cat_id")
    val catId: Int,
    @SerializedName("sub_cat_id")
    val subCatId: Int,
    val name: String,
    val description: String
) {
    val uniqueKey get():String = "sub_to_sub_category_$id"
}
