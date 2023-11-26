package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun verifyUser(
        token: String
    ): Flow<UiState<User>>

    fun loginUser(
        mobile: String,
        password: String
    ): Flow<UiState<User>>

    fun registerUser(
        name: String,
        mobile: String,
        password: String
    ): Flow<UiState<User>>

}