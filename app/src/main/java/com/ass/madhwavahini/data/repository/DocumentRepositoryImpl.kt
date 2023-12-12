package com.ass.madhwavahini.data.repository

import android.app.Application
import android.content.Context
import com.ass.madhwavahini.data.remote.Api.getDocumentExtension
import com.ass.madhwavahini.data.remote.apis.FilesApi
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.getError
import com.ass.madhwavahini.util.isInValidFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File

class DocumentRepositoryImpl(
    private val filesApi: FilesApi,
    private val application: Application
) : DocumentRepository {

    override fun getDocument(
        homeFileName: String, homeFileUrl: String
    ): Flow<UiState<File>> = flow {
        var state = UiState<File>(isLoading = true)
        if (homeFileUrl.isInValidFile()) {
            state = state.copy(
                isLoading = false, error = StringUtil.DynamicText("Invalid file type")
            )
        }
        emit(state)
        val file = File(application.filesDir, homeFileName)
        if (file.exists()) {
            state = state.copy(isLoading = false, data = file)
            emit(state)
            return@flow
        }
        try {
            val result = filesApi.getFilesData(homeFileUrl.getDocumentExtension())
            val body: ResponseBody? = result.body()

            body?.let {
                it.byteStream().use { inputStream ->
                    application.openFileOutput(file.name, Context.MODE_PRIVATE)
                        .use { outputStream -> inputStream.copyTo(outputStream) }
                }
                state = state.copy(isLoading = false, data = file)
            } ?: {
                state = state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Failed to download the file")
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