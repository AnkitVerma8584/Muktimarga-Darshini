package com.ass.madhwavahini.domain.modals

data class User(
    val userId: Int = 0,
    val userName: String = "",
    val userPhone: String = "",
    val isPaidCustomer: Boolean = false,
    val token: String = ""
) {
    val firstName get() = userName.split(" ").first()
}