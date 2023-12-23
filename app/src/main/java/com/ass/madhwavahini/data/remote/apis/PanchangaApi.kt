package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.GET

interface PanchangaApi {

    @GET(Api.GET_PANCHANGA)
    suspend fun getPanchanga(
    ): Response<Result<HomePanchanga>>
}