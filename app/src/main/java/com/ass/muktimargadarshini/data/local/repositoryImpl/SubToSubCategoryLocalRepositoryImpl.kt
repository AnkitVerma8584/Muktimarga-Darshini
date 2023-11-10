package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.SubToSubCategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeSubToSubCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToSubToSubCategoryList
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.repository.local.SubToSubCategoryLocalRepository

class SubToSubCategoryLocalRepositoryImpl(private val subToSubCategoryDao: SubToSubCategoryDao) :
    SubToSubCategoryLocalRepository {
    override suspend fun getSubToSubCategories(
        catId: Int,
        subCatId: Int
    ): List<HomeSubToSubCategory> =
        subToSubCategoryDao.getSubToSubCategories(catId, subCatId).mapToHomeSubToSubCategoryList()

    override suspend fun getSubToSubCategoryCount(catId: Int, subCatId: Int): Int =
        subToSubCategoryDao.getSubToSubCategoryCount(catId, subCatId)

    override suspend fun submitSubToSubCategories(subToSubCategory: List<HomeSubToSubCategory>) {
        subToSubCategoryDao.insertSubToSubCategory(subToSubCategory.mapToSubToSubCategoryList())
    }
}