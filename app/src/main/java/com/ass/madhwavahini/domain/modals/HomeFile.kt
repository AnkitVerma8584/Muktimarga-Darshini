package com.ass.madhwavahini.domain.modals

import com.google.gson.annotations.SerializedName


data class HomeFile(
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
    val godId: Int,
    val authorName: String,
    val godName: String
) {
    val uniqueKey get():String = "file_$id"

    val type
        get():FileType {
            return if (fileUrl.endsWith(".txt", true))
                FileType.TYPE_TXT
            else if (fileUrl.endsWith(".pdf", true))
                FileType.TYPE_PDF
            else if (fileUrl.endsWith(".mp3", true))
                FileType.TYPE_MP3
            else FileType.TYPE_UNKNOWN
        }
    val isTxt get():Boolean = fileUrl.endsWith(".txt", true)
    val isPdf get() = fileUrl.endsWith(".pdf", true)
    val isMusic get() = fileUrl.endsWith(".mp3", true)
}
