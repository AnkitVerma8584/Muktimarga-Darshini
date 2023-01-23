package com.ass.muktimargadarshini.domain.repository.remote
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.flow.Flow

interface FilesRemoteRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<Resource<List<HomeFiles>>>

    fun getFilesData(
        homeFiles: List<HomeFiles>,
    ):Flow<Resource<List<FilesData>>>
}