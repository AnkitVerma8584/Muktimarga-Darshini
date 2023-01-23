package com.ass.muktimargadarshini.domain.repository.remote
import com.ass.muktimargadarshini.domain.modals.HomeSubCategory
import com.ass.muktimargadarshini.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SubCategoryRemoteRepository {
    fun getSubCategories(categoryId: Int): Flow<Resource<List<HomeSubCategory>>>
}