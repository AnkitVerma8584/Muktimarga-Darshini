package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeAradhna
import com.ass.madhwavahini.domain.wrapper.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface AradhnaApi {

    @GET(Api.GET_ARADHNAS)
    suspend fun getAradhnas(
    ): Response<ResultList<HomeAradhna>>
}