package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Api.GET_QUOTE)
    suspend fun getDailyQuote(): Response<Result<String>>

}