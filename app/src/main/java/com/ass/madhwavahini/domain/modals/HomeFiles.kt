package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName

data class HomeFiles(
    val id: Int,
    @SerializedName("cat_id")
    val catId: Int,
    @SerializedName("sub_cat_id")
    val subCatId: Int,
    @SerializedName("sub_to_sub_cat_id")
    val subToSubCatId: Int?,
    val name: String,
    val description: String,
    @SerializedName("file_url")
    val fileUrl: String,
    @SerializedName("author_id")
    val authorId: Int,
    @SerializedName("god_id")
    val godId: Int
) {
    val uniqueKey get():String = "file_$id"
    val isNotPdf get():Boolean = !fileUrl.endsWith(".pdf")
}
