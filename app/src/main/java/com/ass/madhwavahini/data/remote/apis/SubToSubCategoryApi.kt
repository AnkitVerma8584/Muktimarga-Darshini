package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubToSubCategoryApi {
    @GET(Api.GET_SUB_TO_SUBCATEGORIES)
    suspend fun getSubToSubCategories(
        @Query("cat_id") categoryId: Int,
        @Query("sub_cat_id") subCategoryId: Int
    ): Response<ResultList<HomeSubToSubCategory>>

}