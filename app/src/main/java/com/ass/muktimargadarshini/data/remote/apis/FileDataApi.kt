package com.ass.muktimargadarshini.data.remote.apis

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface FileDataApi {
    @GET
    suspend fun getFilesData(
        @Url fileUrl: String
    ): Response<ResponseBody>

}