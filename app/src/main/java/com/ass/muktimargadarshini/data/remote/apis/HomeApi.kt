package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Api.GET_BANNER)
    suspend fun getBannerData(): Response<ResultList<String>>

    @GET(Api.GET_CATEGORY)
    suspend fun getCategoryData(): Response<ResultList<HomeCategory>>
}