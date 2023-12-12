package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.flow.Flow

interface FilesRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<UiStateList<HomeFile>>

    fun getFilesData(
        homeFiles: List<HomeFile>,
    ): Flow<UiStateList<FilesData>>
}