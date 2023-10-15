package com.ass.muktimargadarshini.domain.modals

data class HomeFiles(
    val id: Int,
    val catId: Int,
    val subCatId: Int,
    val subToSubCatId: Int?,
    val name: String,
    val description: String,
    val fileUrl: String,
    val authorId: Int,
    val godId: Int
) {
    val uniqueKey get():String = "file_$id"
    val isNotPdf get():Boolean = !fileUrl.endsWith(".pdf")
}
