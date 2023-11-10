package com.ass.muktimargadarshini.data.remote.repository

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.data.remote.apis.FilesApi
import com.ass.muktimargadarshini.data.remote.mapper.FileMapper.getFileToFilesData
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.local.FileLocalRepository
import com.ass.muktimargadarshini.domain.repository.remote.FilesRemoteRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesDataState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class FilesRemoteRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val filesApi: FilesApi,
    private val fileLocalRepository: FileLocalRepository,
    private val application: Application,
    private val fileDataApi: FileDataApi
) : FilesRemoteRepository {

    override fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<FilesState> = flow {
        var state = FilesState(isLoading = true)
        emit(state)
        val localFiles =
            fileLocalRepository.getFiles(catId, subCategoryId, subToSubCategoryId)

        if (localFiles.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localFiles)
            emit(state)
        }
        try {
            val result =
                filesApi.getFiles(userDataStore.getId(), catId, subCategoryId, subToSubCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data ?: emptyList()
                    if (data != localFiles)
                        fileLocalRepository.submitFiles(data)
                    else return@flow
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

    override fun getFilesData(homeFiles: List<HomeFiles>):
            Flow<FilesDataState> = flow {
        var state = FilesDataState(isLoading = true)
        val fileDataList = mutableListOf<FilesData>()
        emit(state)
        try {
            homeFiles.filter { it.isNotPdf }.forEach { homeFile ->

                val file = File(application.filesDir, "${homeFile.name}_${homeFile.id}.txt")

                val downloadedFile =
                    if (file.exists())
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
            state = if (fileDataList.isEmpty()) {
                state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("No results")
                )
            } else {
                state.copy(
                    isLoading = false,
                    data = fileDataList.toList()
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = StringUtil.DynamicText(e.localizedMessage ?: "Some error occurred.")
            )
        } finally {
            emit(state)
        }
    }
}