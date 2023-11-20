package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.main_content.UserState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun logoutUser(): Flow<UserState>

}