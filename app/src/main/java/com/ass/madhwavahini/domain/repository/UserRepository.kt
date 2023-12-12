package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserId(phone: String): Flow<UiState<Int>>

    fun setPassword(userId: Int, password: String): Flow<UiState<String>>
    fun logoutUser(): Flow<UiState<String>>

}