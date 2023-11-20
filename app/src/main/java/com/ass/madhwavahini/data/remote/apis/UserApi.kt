package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET(Api.USER)
    suspend fun verifyUser(
        @Query("token") token: String,
    ): Response<Result<User>>


    @POST(Api.USER)
    suspend fun logoutUser(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Response<Result<User>>


}