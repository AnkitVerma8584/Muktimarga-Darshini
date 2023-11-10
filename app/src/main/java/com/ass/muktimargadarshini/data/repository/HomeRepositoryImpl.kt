package com.ass.muktimargadarshini.data.repository

import com.ass.muktimargadarshini.data.local.dao.BannerDao
import com.ass.muktimargadarshini.data.local.dao.CategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToBannerList
import com.ass.muktimargadarshini.data.local.mapper.mapToCategory
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToStringList
import com.ass.muktimargadarshini.data.remote.apis.HomeApi
import com.ass.muktimargadarshini.domain.repository.HomeRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState
import com.ass.muktimargadarshini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class HomeRepositoryImpl(
    private val bannerDao: BannerDao,
    private val categoryDao: CategoryDao,
    private val homeApi: HomeApi
) : HomeRepository {

    override fun getCategoryState(): Flow<CategoryState> = flow {
        var state = CategoryState(isLoading = true)
        emit(state)

        val localCategories = categoryDao.getCategories().mapToHomeCategoryList()

        if (localCategories.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localCategories)
            emit(state)
        }

        try {
            val result = homeApi.getCategoryData()
            if (result.isSuccessful && result.body() != null) {

                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!

                    if (data != localCategories)
                        categoryDao.insertCategory(data.mapToCategory())
                    else return@flow

                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection" else {
                        e.localizedMessage ?: "Some server error occurred"
                    }
                )
            )
        } finally {
            emit(state)
        }
    }

    override fun getBannerState(): Flow<BannerState> = flow {
        var state = BannerState(isLoading = true)
        emit(state)

        val localBanners = bannerDao.getBanners().mapToStringList()

        if (localBanners.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localBanners)
            emit(state)
        }

        try {
            val result = homeApi.getBannerData()
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (localBanners != data)
                        bannerDao.insertBanners(data.mapToBannerList())
                    else return@flow
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state =
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText("Unable to fetch")
                    )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}