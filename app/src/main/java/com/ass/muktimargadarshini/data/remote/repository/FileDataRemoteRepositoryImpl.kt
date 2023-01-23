package com.ass.muktimargadarshini.data.remote.repository

import android.app.Application
import android.content.Context
import com.ass.muktimargadarshini.data.remote.Api.getDocumentExtension
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
import com.ass.muktimargadarshini.domain.repository.remote.FileDataRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.util.isInValidFile
import com.ass.muktimargadarshini.util.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException

class FileDataRemoteRepositoryImpl(
    private val fileDataApi: FileDataApi, private val application: Application
) : FileDataRemoteRepository {
    override fun getFileData(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<Resource<File>> = flow {
        emit(Resource.Loading)
        try {
            if (homeFileUrl.isInValidFile())
                emit(Resource.Failure(StringUtil.DynamicText("Invalid file type")))
            val file = File(application.filesDir, homeFileName)
            if (file.exists()) {
                emit(Resource.Cached(file))
            }
            val result = fileDataApi.getFilesData(homeFileUrl.getDocumentExtension())
            result.print()
            result.errorBody().print()
            homeFileUrl.print()
            val body: ResponseBody? = result.body()
            emit(body?.let {
                it.byteStream().use { inputStream ->
                    application.openFileOutput(file.name, Context.MODE_PRIVATE)
                        .use { outputStream -> inputStream.copyTo(outputStream) }
                }
                Resource.Success(file)
            } ?: Resource.Failure(StringUtil.DynamicText("Failed to download file")))

        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException) StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }
}