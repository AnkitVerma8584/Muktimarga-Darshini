package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilesApi {

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int,
        @Query("sub_to_sub_cat_id") subToSubCategoryId: Int
    ): Response<ResultList<HomeFiles>>

}