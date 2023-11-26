package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.local.dao.SubCategoryDao
import com.ass.madhwavahini.data.local.mapper.mapToHomeSubCategoryList
import com.ass.madhwavahini.data.local.mapper.mapToSubCategoryList
import com.ass.madhwavahini.data.remote.apis.SubCategoryApi
import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.domain.repository.SubCategoryRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubCategoryRepositoryImpl(
    private val subCategoryApi: SubCategoryApi,
    private val subCategoryDao: SubCategoryDao,
    private val userDataStore: UserDataStore
) : SubCategoryRepository {

    override fun getSubCategory(categoryId: Int): Flow<UiStateList<HomeSubCategory>> = flow {
        var state = UiStateList<HomeSubCategory>(isLoading = true)
        emit(state)

        val localSubCategories =
            subCategoryDao.getSubCategories(categoryId).mapToHomeSubCategoryList()

        if (localSubCategories.isNotEmpty()) {
            state = state.copy(
                isLoading = false,
                data = localSubCategories
            )
            emit(state)
        }
        try {
            val result = subCategoryApi.getSubCategories(userDataStore.getId(), categoryId)
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (localSubCategories != data) {
                        subCategoryDao.insertSubCategory(data.mapToSubCategoryList())
                    } else return@flow
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
            if (localSubCategories.isEmpty())
                state = state.copy(
                    isLoading = false,
                    error = e.getError()
                )
        } finally {
            emit(state)
        }
    }
}