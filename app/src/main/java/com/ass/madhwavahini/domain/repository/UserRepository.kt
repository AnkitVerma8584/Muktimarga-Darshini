package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun loginUser(
        mobile: String,
        password: String
    ): Flow<LoginState>

    fun registerUser(
        name: String,
        mobile: String,
        password: String
    ): Flow<LoginState>

}