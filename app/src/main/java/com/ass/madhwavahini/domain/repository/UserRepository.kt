package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.main_content.UserState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun verifyUser(): Flow<UserState>

    fun logoutUser(): Flow<UserState>

}