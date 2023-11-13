package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesDataState
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import kotlinx.coroutines.flow.Flow

interface FilesRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<FilesState>

    fun getFilesData(
        homeFiles: List<HomeFile>,
    ): Flow<FilesDataState>
}