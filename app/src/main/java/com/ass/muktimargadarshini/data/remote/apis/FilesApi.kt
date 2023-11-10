package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.utils.ResultList
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
    ): Response<ResultList<HomeFiles>>

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("user_id") userId: Int,
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int,
        @Query("sub_to_sub_cat_id") subToSubCategoryId: Int
    ): Response<ResultList<HomeFiles>>

    @GET
    suspend fun getFilesData(
        @Url fileUrl: String
    ): Response<ResponseBody>

}