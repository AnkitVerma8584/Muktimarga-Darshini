package com.ass.madhwavahini.data.repository

import android.app.Application
import android.content.Context
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.local.dao.FilesDao
import com.ass.madhwavahini.data.local.mapper.mapToFilesList
import com.ass.madhwavahini.data.local.mapper.mapToHomeFilesList
import com.ass.madhwavahini.data.remote.Api.getDocumentExtension
import com.ass.madhwavahini.data.remote.apis.FilesApi
import com.ass.madhwavahini.data.remote.mapper.FileMapper.getFileToFilesData
import com.ass.madhwavahini.domain.modals.FileType
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.repository.FilesRepository
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesDataState
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FilesRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val filesApi: FilesApi,
    private val filesDao: FilesDao,
    private val application: Application
) : FilesRepository {

    override fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<FilesState> = flow {
        var state = FilesState(isLoading = true)
        emit(state)

        val localFiles =
            filesDao.getFiles(catId, subCategoryId, subToSubCategoryId).mapToHomeFilesList()

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
                        filesDao.insertFiles(data.mapToFilesList())
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
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }

    override fun getFilesData(homeFiles: List<HomeFile>):
            Flow<FilesDataState> = flow {
        var state = FilesDataState(isLoading = true)
        val fileDataList = mutableListOf<FilesData>()
        emit(state)
        try {
            homeFiles.filter { it.type == FileType.TYPE_TEXT || it.type == FileType.TYPE_AUDIO }
                .forEach { homeFile ->
                    val file = File(application.filesDir, "file_${homeFile.id}.txt")
                    val downloadedFile =
                        if (file.exists())
                            file
                        else {
                            val result =
                                filesApi.getFilesData(homeFile.fileUrl.getDocumentExtension())
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
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}