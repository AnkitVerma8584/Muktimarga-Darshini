package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET(Api.USER)
    suspend fun getUserId(
        @Query("phone") mobile: String
    ): Response<Result<Int>>

    @FormUrlEncoded
    @POST(Api.USER)
    suspend fun changePassword(
        @Field("id") id: Int,
        @Field("password") password: String
    ): Response<Result<String>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = Api.LOGIN, hasBody = true)
    suspend fun logoutUser(
        @Field("user_id") id: Int
    ): Response<Result<String>>


}