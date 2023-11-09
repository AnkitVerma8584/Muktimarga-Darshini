package com.ass.muktimargadarshini.data.remote.apis

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.domain.utils.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET(Api.LOGIN)
    suspend fun loginUser(
        @Query("mobile") mobile: String,
        @Query("password") password: String
    ): Response<Result<User>>

    @FormUrlEncoded
    @POST(Api.LOGIN)
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Response<Result<User>>

}