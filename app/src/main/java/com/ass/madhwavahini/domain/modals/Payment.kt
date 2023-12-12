package com.ass.madhwavahini.domain.modals

data class Payment(
    val orderId: String,
    val amount: Int,
    val upi: String,
    val phone: String,
)
