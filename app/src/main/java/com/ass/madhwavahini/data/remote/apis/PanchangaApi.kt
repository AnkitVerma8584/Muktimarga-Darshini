package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface PanchangaApi {

    @GET(Api.GET_PANCHANGA)
    suspend fun getPanchanga(
        @Query("date") date: String ,
    ): Response<Result<HomePanchanga>>
}