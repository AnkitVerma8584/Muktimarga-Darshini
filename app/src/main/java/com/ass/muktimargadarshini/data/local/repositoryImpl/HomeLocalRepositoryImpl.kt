package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.BannerDao
import com.ass.muktimargadarshini.data.local.dao.CategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToBannerList
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeList
import com.ass.muktimargadarshini.data.local.mapper.mapToStringList
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.repository.local.HomeLocalRepository

class HomeLocalRepositoryImpl(
    private val bannerDao: BannerDao,
    private val categoryDao: CategoryDao
) :
    HomeLocalRepository {
    override suspend fun hasCachedBanners(): Boolean = bannerDao.getBannerCount() != 0

    override suspend fun getBanners(): List<String> = bannerDao.getBanners().mapToStringList()

    override suspend fun hasCachedCategories(): Boolean = categoryDao.getCategoryCount() != 0

    override suspend fun getCategories(): List<HomeCategory> =
        categoryDao.getCategories().mapToHomeCategoryList()

    override suspend fun submitBanners(bannerList: List<String>) {
        bannerDao.insertBanners(bannerList.mapToBannerList())
    }

    override suspend fun submitCategories(categoryList: List<HomeCategory>) {
        categoryDao.insertCategory(categoryList.mapToHomeList())
    }
}