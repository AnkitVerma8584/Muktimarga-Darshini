package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import kotlinx.coroutines.flow.Flow

interface SubToSubCategoryRepository {

    fun getSubToSubCategories(
        categoryId: Int,
        subCategoryId: Int
    ): Flow<SubToSubCategoryState>

    fun getFiles(catId: Int, subCategoryId: Int): Flow<FilesState>

    fun getFilesData(
        homeFiles: List<HomeFiles>?,
    ): Flow<List<FilesData>>

    fun getAuthors(): Flow<List<HomeAuthor>>

    fun getGods(): Flow<List<HomeGod>>
}