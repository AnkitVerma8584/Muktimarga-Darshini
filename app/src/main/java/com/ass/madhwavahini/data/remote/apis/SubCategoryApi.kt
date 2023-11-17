package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubCategoryApi {

    @GET(Api.GET_SUBCATEGORIES)
    suspend fun getSubCategories(
        @Query("user_id") userId: Int,
        @Query("cat_id") categoryId: Int
    ): Response<ResultList<HomeSubCategory>>
}