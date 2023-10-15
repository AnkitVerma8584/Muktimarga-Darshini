package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.domain.utils.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface DataApi {
    @GET(Api.GET_AUTHOR)
    suspend fun getAuthor(): Response<ResultList<HomeAuthor>>

    @GET(Api.GET_GODS)
    suspend fun getGods(): Response<ResultList<HomeGod>>
}