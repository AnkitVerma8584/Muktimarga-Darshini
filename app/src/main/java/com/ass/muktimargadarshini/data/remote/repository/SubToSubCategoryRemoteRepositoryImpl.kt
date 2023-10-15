package com.ass.muktimargadarshini.data.remote.repository

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.data.remote.apis.SubToSubCategoryApi
import com.ass.muktimargadarshini.data.remote.mapper.FileMapper.getFileToFilesData
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.repository.local.FileLocalRepository
import com.ass.muktimargadarshini.domain.repository.local.SubToSubCategoryLocalRepository
import com.ass.muktimargadarshini.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class SubToSubCategoryRemoteRepositoryImpl(
    private val subToSubCategoryApi: SubToSubCategoryApi,
    private val subToSubCategoryLocalRepository: SubToSubCategoryLocalRepository,
    private val fileLocalRepository: FileLocalRepository,
    private val application: Application,
    private val fileDataApi: FileDataApi
) : SubToSubCategoryRemoteRepository {

    override fun getSubToSubCategories(
        categoryId: Int, subCategoryId: Int
    ): Flow<Resource<List<HomeSubToSubCategory>>> = flow {
        emit(Resource.Loading)
        if (subToSubCategoryLocalRepository.getSubToSubCategoryCount(
                categoryId,
                subCategoryId
            ) > 0
        ) emit(
            Resource.Cached(
                subToSubCategoryLocalRepository.getSubToSubCategories(
                    categoryId, subCategoryId
                )
            )
        )
        emit(
            try {
                val result = subToSubCategoryApi.getSubToSubCategories(categoryId, subCategoryId)
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        subToSubCategoryLocalRepository.submitSubToSubCategories(data)
                        Resource.Success(data)
                    } else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
                } else {
                    Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
                }
            } catch (e: Exception) {
                Resource.Failure(
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }

    override fun getFiles(catId: Int, subCategoryId: Int): Flow<Resource<List<HomeFiles>>> = flow {
        emit(Resource.Loading)
        if (fileLocalRepository.getFilesCount(catId, subCategoryId) > 0)
            emit(Resource.Success(fileLocalRepository.getFiles(catId, subCategoryId)))
        emit(
            try {
                val result = subToSubCategoryApi.getFiles(catId, subCategoryId)
                result.print()
                if (result.isSuccessful && result.body() != null) {
                    if (result.body()!!.success) {
                        val data = result.body()?.data ?: emptyList()
                        fileLocalRepository.submitFiles(data)
                        Resource.Success(data)
                    } else Resource.Failure(StringUtil.DynamicText(result.body()!!.message))
                } else {
                    Resource.Failure(StringUtil.DynamicText("Please check your internet connection"))
                }
            } catch (e: Exception) {
                Resource.Failure(
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            }
        )
    }

    override fun getFilesData(homeFiles: List<HomeFiles>): Flow<Resource<List<FilesData>>> = flow {
        emit(Resource.Loading)
        try {
            val fileDataList = mutableListOf<FilesData>()
            homeFiles.filter { it.isNotPdf }.forEach { homeFile ->
                val file = File(application.filesDir, "${homeFile.name}_${homeFile.id}.txt")

                val downloadedFile = if (!file.exists()) {
                    val result = fileDataApi.getFilesData(homeFile.fileUrl.getDocumentExtension())
                    result.body()?.byteStream()?.use { inputStream ->
                        application.openFileOutput(file.name, Context.MODE_PRIVATE)
                            .use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        file
                    }
                } else file

                val fileData = downloadedFile?.let { homeFile.getFileToFilesData(it) }
                fileData?.let {
                    fileDataList.add(it)
                }
            }
            if (fileDataList.isEmpty())
                emit(Resource.Failure(StringUtil.DynamicText("No results")))
            else emit(Resource.Success(fileDataList.toList()))
        } catch (e: Exception) {
            emit(Resource.Failure(StringUtil.DynamicText("No results ${e.message}")))
        }
    }
}