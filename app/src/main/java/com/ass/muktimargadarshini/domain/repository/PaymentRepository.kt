package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.domain.modals.Payment
import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.ui.presentation.payment.PaymentState
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentOrder(): Flow<PaymentState<Payment>>

    fun verifyPayment(
        orderId: String,
        paymentId: String,
        paymentSignature: String,
    ): Flow<PaymentState<User>>

}