package com.ass.muktimargadarshini.data.remote.repository

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.data.remote.apis.SubToSubCategoryApi
import com.ass.muktimargadarshini.data.remote.mapper.FileMapper.getFileToFilesData
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.local.FileLocalRepository
import com.ass.muktimargadarshini.domain.repository.local.SubToSubCategoryLocalRepository
import com.ass.muktimargadarshini.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class SubToSubCategoryRemoteRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val subToSubCategoryApi: SubToSubCategoryApi,
    private val subToSubCategoryLocalRepository: SubToSubCategoryLocalRepository,
    private val fileLocalRepository: FileLocalRepository,
    private val application: Application,
    private val fileDataApi: FileDataApi
) : SubToSubCategoryRemoteRepository {

    override fun getSubToSubCategories(
        categoryId: Int, subCategoryId: Int
    ): Flow<SubToSubCategoryState> = flow {
        var state = SubToSubCategoryState(isLoading = true)
        emit(state)

        val localSubToSubCategories =
            subToSubCategoryLocalRepository.getSubToSubCategories(categoryId, subCategoryId)

        if (localSubToSubCategories.isNotEmpty()) {
            state = state.copy(
                isLoading = false,
                data = localSubToSubCategories
            )
            emit(state)
        }

        try {
            val result = subToSubCategoryApi.getSubToSubCategories(categoryId, subCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data ?: emptyList()
                    if (data != localSubToSubCategories)
                        subToSubCategoryLocalRepository.submitSubToSubCategories(data)
                    state.copy(isLoading = false, data = data)
                } else state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText(result.body()!!.message)
                )
            } else {
                state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error =
                StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection"
                    else e.localizedMessage ?: "Some server error occurred"

                )
            )
        } finally {
            emit(state)
        }
    }

    override fun getFiles(catId: Int, subCategoryId: Int): Flow<FilesState> = flow {
        var state = FilesState(isLoading = true)
        emit(state)

        val localFiles =
            fileLocalRepository.getFiles(catId, subCategoryId)

        if (localFiles.isNotEmpty()) {
            state = state.copy(
                isLoading = false,
                data = localFiles
            )
            emit(state)
        }

        try {
            val result = subToSubCategoryApi.getFiles(userDataStore.getId(), catId, subCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data ?: emptyList()
                    if (data != localFiles)
                        fileLocalRepository.submitFiles(data)
                    state.copy(isLoading = false, data = data)
                } else state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText(result.body()!!.message)
                )
            } else {
                state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection"
                    else e.localizedMessage ?: "Some server error occurred"
                )
            )
        } finally {
            emit(state)
        }
    }

    override fun getFilesData(homeFiles: List<HomeFiles>): Flow<List<FilesData>> = flow {
        try {
            val fileDataList = mutableListOf<FilesData>()
            homeFiles.filter { it.isNotPdf }.forEach { homeFile ->
                val file = File(application.filesDir, "file_${homeFile.id}.txt")

                val downloadedFile = if (file.exists())
                    file
                else {
                    val result =
                        fileDataApi.getFilesData(homeFile.fileUrl.getDocumentExtension())
                    result.body()?.byteStream()?.use { inputStream ->
                        application.openFileOutput(file.name, Context.MODE_PRIVATE)
                            .use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        file
                    }
                }
                val fileData = downloadedFile?.let { homeFile.getFileToFilesData(it) }
                fileData?.let {
                    fileDataList.add(it)
                }
            }
            emit(fileDataList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}