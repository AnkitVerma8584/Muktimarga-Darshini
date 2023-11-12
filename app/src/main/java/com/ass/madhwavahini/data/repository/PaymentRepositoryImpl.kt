package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.PaymentApi
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.repository.PaymentRepository
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.payment.PaymentState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PaymentRepositoryImpl(
    private val paymentApi: PaymentApi,
    private val userDataStore: UserDataStore
) : PaymentRepository {
    override fun getPaymentOrder(): Flow<PaymentState<Payment>> = flow {
        var state = PaymentState<Payment>(isLoading = true)
        emit(state)
        try {
            val result = paymentApi.getOrder(
                userId = userDataStore.getId(),
                userMobile = userDataStore.getPhone()
            )
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }

    override fun verifyPayment(
        orderId: String,
        paymentId: String,
        paymentSignature: String
    ): Flow<PaymentState<User>> = flow {
        var state = PaymentState<User>(isLoading = true)
        emit(state)
        try {
            val result = paymentApi.verifyPayment(
                userDataStore.getId(),
                orderId,
                paymentId,
                paymentSignature
            )

            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}