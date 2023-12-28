package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.dao.CategoryDao
import com.ass.madhwavahini.data.local.mapper.mapToCategory
import com.ass.madhwavahini.data.local.mapper.mapToHomeCategoryList
import com.ass.madhwavahini.data.remote.apis.CategoryApi
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.repository.CategoryRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val categoryApi: CategoryApi
) : CategoryRepository {
    override fun getCategoryState(): Flow<UiStateList<HomeCategory>> = flow {
        var state = UiStateList<HomeCategory>(isLoading = true)
        emit(state)

        val localCategories = categoryDao.getCategories().mapToHomeCategoryList()

        if (localCategories.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localCategories)
            emit(state)
        }
        try {
            val result = categoryApi.getCategoryData()
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
            if (localCategories.isEmpty())
                state = state.copy(
                    isLoading = false,
                    error = e.getError()
                )
        } finally {
            emit(state)
        }
    }
}