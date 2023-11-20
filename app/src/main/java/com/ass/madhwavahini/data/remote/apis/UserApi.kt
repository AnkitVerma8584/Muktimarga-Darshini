package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP

interface UserApi {

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Api.LOGIN, hasBody = true)
    suspend fun logoutUser(
        @Field("user_id") id: Int
    ): Response<Result<String>>


}