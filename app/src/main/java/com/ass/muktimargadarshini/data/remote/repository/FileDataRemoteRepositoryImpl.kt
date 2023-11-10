package com.ass.muktimargadarshini.data.remote.repository

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.domain.repository.remote.FileDataRemoteRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.modals.FileDataState
import com.ass.muktimargadarshini.util.isInValidFile
import com.ass.muktimargadarshini.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException

class FileDataRemoteRepositoryImpl(
    private val fileDataApi: FileDataApi,
    private val application: Application
) : FileDataRemoteRepository {

    override fun getFileData(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<FileDataState> = flow {

        var state = FileDataState(isLoading = true)
        homeFileName.print()
        homeFileUrl.print()
        if (homeFileUrl.isInValidFile()) {
            state = state.copy(
                isLoading = false,
                error = StringUtil.DynamicText("Invalid file type")
            )
        }
        emit(state)
        try {
            val file = File(application.filesDir, homeFileName)
            if (file.exists()) {
                state = state.copy(isLoading = false, data = file)
                emit(state)
            }
            val result = fileDataApi.getFilesData(homeFileUrl.getDocumentExtension())
            val body: ResponseBody? = result.body()

            body?.let {
                it.byteStream().use { inputStream ->
                    application.openFileOutput(file.name, Context.MODE_PRIVATE)
                        .use { outputStream -> inputStream.copyTo(outputStream) }
                }
                state = state.copy(isLoading = false, data = file)
                emit(state)
            } ?: {
                state = state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Failed to download the file")
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
}