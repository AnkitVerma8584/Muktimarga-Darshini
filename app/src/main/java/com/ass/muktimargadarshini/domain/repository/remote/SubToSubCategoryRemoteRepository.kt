package com.ass.muktimargadarshini.domain.repository.remote

import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.flow.Flow

interface SubToSubCategoryRemoteRepository {

    fun getSubToSubCategories(
        categoryId: Int,
        subCategoryId: Int
    ): Flow<Resource<List<HomeSubToSubCategory>>>

    fun getFiles(catId: Int, subCategoryId: Int): Flow<Resource<List<HomeFiles>>>

    fun getFilesData(
        homeFiles: List<HomeFiles>,
    ): Flow<Resource<List<FilesData>>>

}