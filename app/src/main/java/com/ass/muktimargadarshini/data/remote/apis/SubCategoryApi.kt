package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeSubCategory
import com.ass.muktimargadarshini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SubCategoryApi {

    @GET(Api.GET_SUBCATEGORIES)
    suspend fun getSubCategories(
        @Query("cat_id") categoryId: Int
    ): Response<ResultList<HomeSubCategory>>
}