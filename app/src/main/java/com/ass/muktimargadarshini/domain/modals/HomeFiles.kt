package com.ass.muktimargadarshini.domain.modals

data class HomeFiles(
    val id: Int,
    val cat_id: Int,
    val sub_cat_id: Int,
    val sub_to_sub_cat_id: Int?,
    val name: String,
    val description: String,
    val file_url: String,
    val author_id: Int,
    val god_id: Int
) {
    val uniqueKey get():String = "file_$id"
    val isNotPdf get():Boolean = !file_url.endsWith(".pdf")
}
