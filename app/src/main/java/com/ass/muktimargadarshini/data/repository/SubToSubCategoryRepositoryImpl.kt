package com.ass.muktimargadarshini.data.repository
/*
import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.local.dao.AuthorDao
import com.ass.muktimargadarshini.data.local.dao.FilesDao
import com.ass.muktimargadarshini.data.local.dao.GodDao
import com.ass.muktimargadarshini.data.local.dao.SubToSubCategoryDao
import com.ass.muktimargadarshini.data.local.mapper.mapToAuthor
import com.ass.muktimargadarshini.data.local.mapper.mapToFilesList
import com.ass.muktimargadarshini.data.local.mapper.mapToGod
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeAuthor
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeFilesList
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeGod
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeSubToSubCategoryList
import com.ass.muktimargadarshini.data.local.mapper.mapToSubToSubCategoryList
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.DataApi
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.data.remote.apis.SubToSubCategoryApi
import com.ass.muktimargadarshini.data.remote.mapper.FileMapper.getFileToFilesData
import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.domain.repository.SubToSubCategoryRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class SubToSubCategoryRepositoryImpl(
    private val application: Application,
    private val subToSubCategoryApi: SubToSubCategoryApi,
    private val subToSubCategoryDao: SubToSubCategoryDao,
    private val filesDao: FilesDao,
    private val fileDataApi: FileDataApi,
    private val dataApi: DataApi,
    private val godDao: GodDao,
    private val authorDao: AuthorDao,
    private val userDataStore: UserDataStore
) : SubToSubCategoryRepository {

    override fun getSubToSubCategories(
        categoryId: Int, subCategoryId: Int
    ): Flow<SubToSubCategoryState> = flow {
        var state = SubToSubCategoryState()
        state = if (subToSubCategoryDao.getSubToSubCategoryCount(
                categoryId,
                subCategoryId
            ) == 0
        ) state.copy(isLoading = true)
        else state.copy(
            data = subToSubCategoryDao.getSubToSubCategories(categoryId, subCategoryId)
                .mapToHomeSubToSubCategoryList()
        )
        emit(state)
        try {
            val result = subToSubCategoryApi.getSubToSubCategories(categoryId, subCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    subToSubCategoryDao.insertSubToSubCategory(data.mapToSubToSubCategoryList())
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false, error = StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection" else {
                        e.localizedMessage ?: "Some server error occurred"
                    }
                )
            )
        } finally {
            emit(state)
        }
    }

    override fun getFiles(catId: Int, subCategoryId: Int): Flow<FilesState> = flow {
        var state = FilesState()
        state = if (filesDao.getFileCount(catId, subCategoryId) == 0) state.copy(isLoading = true)
        else state.copy(
            data = filesDao.getFiles(catId, subCategoryId).mapToHomeFilesList()
        )
        emit(state)
        try {
            val result = subToSubCategoryApi.getFiles(userDataStore.getId(), catId, subCategoryId)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    filesDao.insertFiles(data.mapToFilesList())
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false, error = StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection" else {
                        e.localizedMessage ?: "Some server error occurred"
                    }
                )
            )
        } finally {
            emit(state)
        }
    }

    override fun getFilesData(homeFiles: List<HomeFiles>?): Flow<List<FilesData>> = flow {
        val fileDataList = mutableListOf<FilesData>()
        try {
            homeFiles?.filter { it.isNotPdf }?.forEach { homeFile ->
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
            emit(fileDataList.toList())
        } catch (_: Exception) {
        } finally {
            emit(fileDataList.toList())
        }
    }

    override fun getAuthors(): Flow<List<HomeAuthor>> = flow {
        val homeAuthors = mutableSetOf<HomeAuthor>()
        homeAuthors.addAll(authorDao.getAuthors().mapToHomeAuthor())
        try {
            val result = dataApi.getAuthor()
            if (result.isSuccessful && result.body() != null && result.body()!!.success) {
                val data = result.body()?.data!!
                authorDao.insertAuthors(data.mapToAuthor())
                homeAuthors.clear()
                homeAuthors.addAll(data)
            }
        } catch (_: Exception) {
        } finally {
            emit(homeAuthors.toList())
        }
    }

    override fun getGods(): Flow<List<HomeGod>> = flow {
        val homeGods = mutableSetOf<HomeGod>()
        homeGods.addAll(godDao.getGods().mapToHomeGod())
        try {
            val result = dataApi.getGods()
            if (result.isSuccessful && result.body() != null && result.body()!!.success) {
                val data = result.body()?.data!!
                godDao.insertGods(data.mapToGod())
                homeGods.clear()
                homeGods.addAll(data)
            }
        } catch (_: Exception) {
        } finally {
            emit(homeGods.toList())
        }
    }
}*/
