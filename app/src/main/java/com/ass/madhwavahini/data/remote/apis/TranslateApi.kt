package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TranslateApi {

    @FormUrlEncoded
    @POST(Api.TRANSLATE)
    suspend fun translateText(
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String,
        @Field("nativize") nativize: Boolean = false,
        @Field("preoptions") preoptions: String? = null,
        @Field("postoptions") postoptions: String? = null
    ): Response<String>
}