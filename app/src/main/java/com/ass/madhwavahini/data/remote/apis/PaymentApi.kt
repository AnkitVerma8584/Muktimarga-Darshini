package com.ass.madhwavahini.data.remote.apis

import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentApi {
    @GET(Api.PAYMENT)
    suspend fun getOrder(
        @Query("user_id") userId: Int,
        @Query("user_phone") userMobile: String
    ): Response<Result<Payment>>

    @FormUrlEncoded
    @POST(Api.PAYMENT)
    suspend fun verifyPayment(
        @Field("user_id") userId: Int,
        @Field("order_id") orderId: String,
        @Field("payment_id") paymentId: String,
        @Field("payment_signature") paymentSignature: String
    ): Response<Result<User>>
}