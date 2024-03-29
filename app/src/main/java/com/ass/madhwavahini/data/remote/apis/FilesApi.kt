package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.wrapper.ResultList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface FilesApi {

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("user_id") userId: Int,
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int
    ): Response<ResultList<HomeFile>>

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("user_id") userId: Int,
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int,
        @Query("sub_to_sub_cat_id") subToSubCategoryId: Int
    ): Response<ResultList<HomeFile>>

    @GET
    suspend fun getFilesData(
        @Url fileUrl: String
    ): Response<ResponseBody>

}