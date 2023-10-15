package com.ass.muktimargadarshini.domain.modals

data class HomeSubToSubCategory(
    val id: Int,
    val catId: Int,
    val subCatId: Int,
    val name: String,
    val description: String
) {
    val uniqueKey get():String = "sub_to_sub_category_$id"
}
