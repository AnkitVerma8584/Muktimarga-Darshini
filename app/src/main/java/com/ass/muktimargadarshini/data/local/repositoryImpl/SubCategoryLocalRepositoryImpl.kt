package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.SubCategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeSubCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToSubCategoryList
import com.ass.muktimargadarshini.domain.modals.HomeSubCategory
import com.ass.muktimargadarshini.domain.repository.local.SubCategoryLocalRepository

class SubCategoryLocalRepositoryImpl(private val subCategoryDao: SubCategoryDao) :
    SubCategoryLocalRepository {

    override suspend fun getSubCategories(id: Int): List<HomeSubCategory> =
        subCategoryDao.getSubCategories(id).mapToHomeSubCategoryList()

    override suspend fun getSubCategoryCount(id: Int): Int = subCategoryDao.getSubCategoryCount(id)

    override suspend fun submitSubCategories(subCategory: List<HomeSubCategory>) {
        subCategoryDao.insertCategory(subCategory.mapToSubCategoryList())
    }
}