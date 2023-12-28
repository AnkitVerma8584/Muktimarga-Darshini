package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.HomeGallery
import com.ass.madhwavahini.domain.wrapper.ResultList
import retrofit2.Response
import retrofit2.http.GET

interface GalleryApi {
    @GET(Api.GET_GALLERY)
    suspend fun getGalleryData(): Response<ResultList<HomeGallery>>

}