package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.UserApi
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) : UserRepository {
    override fun loginUser(mobile: String, password: String): Flow<LoginState> = flow {
        var state = LoginState(isLoading = true)
        emit(state)
        try {
            val result = userApi.loginUser(mobile, password)
            if (result.isSuccessful && result.body() != null) {

                state = if (result.body()!!.success) {
                    val data = result.body()?.data
                    userDataStore.saveUser(data!!)
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = e.getError()
            )
        } finally {
            emit(state)
        }
    }

    override fun registerUser(name: String, mobile: String, password: String): Flow<LoginState> =
        flow {
            var state = LoginState(isLoading = true)
            emit(state)
            try {
                val result = userApi.registerUser(name, mobile, password)
                if (result.isSuccessful && result.body() != null) {
                    state = if (result.body()!!.success) {
                        val data = result.body()?.data
                        userDataStore.saveUser(data!!)
                        state.copy(isLoading = false, data = data)
                    } else {
                        state.copy(
                            isLoading = false,
                            error = StringUtil.DynamicText(result.body()!!.message)
                        )
                    }
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText("Unable to fetch data.")
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.getError()
                )
            } finally {
                emit(state)
            }
        }
}