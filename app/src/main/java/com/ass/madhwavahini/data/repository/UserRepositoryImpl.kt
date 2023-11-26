package com.ass.madhwavahini.data.repository

import android.app.Application
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.UserApi
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val application: Application,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) : UserRepository {
    override fun getUserId(phone: String): Flow<UiState<Int>> = flow {
        var state = UiState<Int>(isLoading = true)
        emit(state)
        try {
            val result = userApi.getUserId(phone)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(isLoading = false, error = e.getError())
        } finally {
            emit(state)
        }
    }

    override fun setPassword(userId: Int, password: String): Flow<UiState<String>> = flow {
        var state = UiState<String>(isLoading = true)
        emit(state)
        try {
            val result = userApi.changePassword(userId, password)
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false, error = e.getError()
            )
        } finally {
            emit(state)
        }
    }

    override fun logoutUser(): Flow<UiState<String>> = flow {
        var state = UiState<String>(isLoading = true)
        emit(state)
        try {
            val result = userApi.logoutUser(userDataStore.getId())
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data
                    userDataStore.logout()
                    if (application.filesDir.isDirectory) for (file in application.filesDir.listFiles()!!) {
                        file.delete()
                    }
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false, error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}