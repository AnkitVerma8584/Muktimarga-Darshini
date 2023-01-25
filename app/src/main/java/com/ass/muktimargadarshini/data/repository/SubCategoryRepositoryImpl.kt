package com.ass.muktimargadarshini.data.repository

import com.ass.muktimargadarshini.data.local.dao.SubCategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeSubCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToSubCategoryList
import com.ass.muktimargadarshini.data.remote.apis.SubCategoryApi
import com.ass.muktimargadarshini.domain.repository.SubCategoryRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category.SubCategoryState
import com.ass.muktimargadarshini.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SubCategoryRepositoryImpl(
    private val subCategoryApi: SubCategoryApi,
    private val subCategoryDao: SubCategoryDao
) : SubCategoryRepository {

    override fun getSubCategory(categoryId: Int): Flow<SubCategoryState> = flow {
        var state = SubCategoryState()
        state = if (subCategoryDao.getSubCategoryCount(categoryId) == 0)
            state.copy(isLoading = true)
        else
            state.copy(
                data = subCategoryDao.getSubCategories(categoryId).mapToHomeSubCategoryList()
            )
        emit(state)
        try {
            val result = subCategoryApi.getSubCategories(categoryId)
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    subCategoryDao.insertSubCategory(data.mapToSubCategoryList())
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
}