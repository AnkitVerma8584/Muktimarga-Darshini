package com.ass.madhwavahini.data.repository

import android.app.Application
import android.content.Context
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.local.dao.FilesDao
import com.ass.madhwavahini.data.local.dao.SubToSubCategoryDao
import com.ass.madhwavahini.data.local.mapper.mapToFilesList
import com.ass.madhwavahini.data.local.mapper.mapToHomeFilesList
import com.ass.madhwavahini.data.local.mapper.mapToHomeSubToSubCategoryList
import com.ass.madhwavahini.data.local.mapper.mapToSubToSubCategoryList
import com.ass.madhwavahini.data.remote.Api.getDocumentExtension
import com.ass.madhwavahini.data.remote.apis.FilesApi
import com.ass.madhwavahini.data.remote.apis.SubToSubCategoryApi
import com.ass.madhwavahini.data.remote.mapper.getFileToFilesData
import com.ass.madhwavahini.domain.modals.FileType
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.repository.SubToSubCategoryRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class SubToSubCategoryRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val subToSubCategoryApi: SubToSubCategoryApi,
    private val subToSubCategoryDao: SubToSubCategoryDao,
    private val filesDao: FilesDao,
    private val application: Application,
    private val filesApi: FilesApi
) : SubToSubCategoryRepository {

    override fun getSubToSubCategories(
        categoryId: Int, subCategoryId: Int
    ): Flow<SubToSubCategoryState> = flow {
        var state = SubToSubCategoryState(isLoading = true)
        emit(state)

        val localSubToSubCategories =
            subToSubCategoryDao.getSubToSubCategories(categoryId, subCategoryId)
                .mapToHomeSubToSubCategoryList()

        if (localSubToSubCategories.isNotEmpty()) {
            state = state.copy(
                isLoading = false, data = localSubToSubCategories
            )
            emit(state)
        }

        try {
            val result = subToSubCategoryApi.getSubToSubCategories(
                userDataStore.getId(),
                categoryId,
                subCategoryId
            )
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data ?: emptyList()

                    if (data != localSubToSubCategories) subToSubCategoryDao
                        .insertSubToSubCategory(data.mapToSubToSubCategoryList())
                    else return@flow

                    state.copy(isLoading = false, data = data)
                } else state.copy(
                    isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                )
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            if (localSubToSubCategories.isEmpty())
                state = state.copy(
                    isLoading = false, error = e.getError()
                )
        } finally {
            emit(state)
        }
    }

    override fun getFiles(catId: Int, subCategoryId: Int): Flow<FilesState> = flow {
        var state = FilesState(isLoading = true)
        emit(state)

        val localFiles = filesDao.getFiles(catId, subCategoryId).mapToHomeFilesList()

        if (localFiles.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localFiles)
            emit(state)
        }
        try {
            val result = filesApi.getFiles(userDataStore.getId(), catId, subCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data ?: emptyList()
                    if (data != localFiles) filesDao.insertFiles(data.mapToFilesList())
                    else return@flow

                    state.copy(isLoading = false, data = data)
                } else state.copy(
                    isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                )
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            if (localFiles.isEmpty())
                state = state.copy(
                    isLoading = false, error = e.getError()
                )
        } finally {
            emit(state)
        }
    }

    override fun getFilesData(homeFiles: List<HomeFile>): Flow<List<FilesData>> = flow {
        try {
            val fileDataList = mutableListOf<FilesData>()
            homeFiles.filter { it.type == FileType.TYPE_TEXT || it.type == FileType.TYPE_AUDIO }
                .forEach { homeFile ->
                    val file = File(application.filesDir, "${homeFile.uniqueKey}.txt")

                    val downloadedFile = if (file.exists()) file
                    else {
                        val result = filesApi.getFilesData(homeFile.fileUrl.getDocumentExtension())
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