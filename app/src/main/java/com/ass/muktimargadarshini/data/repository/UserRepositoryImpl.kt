package com.ass.muktimargadarshini.data.repository

import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.remote.apis.UserApi
import com.ass.muktimargadarshini.domain.repository.UserRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.authentication.model.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

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
                error = StringUtil.DynamicText(
                    if (e is IOException) "Please check your internet connection" else {
                        e.localizedMessage ?: "Some server error occurred"
                    }
                )
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
                    error = StringUtil.DynamicText(
                        if (e is IOException) "Please check your internet connection" else {
                            e.localizedMessage ?: "Some server error occurred"
                        }
                    )
                )
            } finally {
                emit(state)
            }
        }
}