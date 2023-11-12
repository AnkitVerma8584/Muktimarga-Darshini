package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeFiles
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import kotlinx.coroutines.flow.Flow

interface SubToSubCategoryRepository {

    fun getSubToSubCategories(
        categoryId: Int,
        subCategoryId: Int
    ): Flow<SubToSubCategoryState>

    fun getFiles(catId: Int, subCategoryId: Int): Flow<FilesState>

    fun getFilesData(
        homeFiles: List<HomeFiles>,
    ): Flow<List<FilesData>>

}