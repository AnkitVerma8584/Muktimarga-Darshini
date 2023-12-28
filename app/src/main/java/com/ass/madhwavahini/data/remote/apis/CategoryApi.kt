package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.wrapper.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    @GET(Api.GET_CATEGORY)
    suspend fun getCategoryData(): Response<ResultList<HomeCategory>>
}