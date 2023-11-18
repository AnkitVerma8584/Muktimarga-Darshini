package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.UserApi
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) : UserRepository {

    override fun verifyUser(): Flow<LoginState> {
        TODO("Not yet implemented")
    }

    override fun logoutUser(): Flow<LoginState> {
        TODO("Not yet implemented")
    }
}