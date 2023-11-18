package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun verifyUser(): Flow<LoginState>

    fun logoutUser(): Flow<LoginState>

}