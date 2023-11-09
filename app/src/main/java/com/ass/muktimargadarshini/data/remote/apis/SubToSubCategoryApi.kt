package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubToSubCategoryApi {
    @GET(Api.GET_SUB_TO_SUBCATEGORIES)
    suspend fun getSubToSubCategories(
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int
    ): Response<ResultList<HomeSubToSubCategory>>

    @GET(Api.GET_FILES)
    suspend fun getFiles(
        @Query("user_id") userId:Int,
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int
    ): Response<ResultList<HomeFiles>>
}