package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.payment.PaymentState
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentOrder(): Flow<PaymentState<Payment>>

    fun verifyPayment(
        orderId: String,
        paymentId: String,
        paymentSignature: String,
    ): Flow<PaymentState<User>>

}