package com.ass.muktimargadarshini.domain.repository.local

import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory

interface SubToSubCategoryLocalRepository {

    suspend fun getSubToSubCategories(catId: Int, subCatId: Int): List<HomeSubToSubCategory>

    suspend fun getSubToSubCategoryCount(catId: Int, subCatId: Int): Int

    suspend fun submitSubToSubCategories(subToSubCategory: List<HomeSubToSubCategory>)

}