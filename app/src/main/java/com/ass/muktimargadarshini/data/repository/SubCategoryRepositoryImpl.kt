package com.ass.muktimargadarshini.data.repository

import com.ass.muktimargadarshini.data.local.dao.SubCategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeSubCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToSubCategoryList
import com.ass.muktimargadarshini.data.remote.apis.SubCategoryApi
import com.ass.muktimargadarshini.domain.repository.SubCategoryRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_category.components.SubCategoryState
import com.ass.muktimargadarshini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubCategoryRepositoryImpl(
    private val subCategoryApi: SubCategoryApi,
    private val subCategoryDao: SubCategoryDao
) : SubCategoryRepository {

    override fun getSubCategory(categoryId: Int): Flow<SubCategoryState> = flow {
        var state = SubCategoryState(isLoading = true)
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
            val result = subCategoryApi.getSubCategories(categoryId)
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (localSubCategories != data)
                        subCategoryDao.insertSubCategory(data.mapToSubCategoryList())
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
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}