package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.UserApi
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.ui.presentation.main_content.UserState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) : UserRepository {

    override fun verifyUser(): Flow<UserState> = flow {
        emit(UserState(data = true))
    }

    override fun logoutUser(): Flow<UserState> = flow {
        emit(UserState(data = true))
    }
}