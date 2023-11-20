package com.ass.madhwavahini.data.repository

import android.app.Application
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.UserApi
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.ui.presentation.main_content.UserState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val application: Application,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) : UserRepository {
    override fun logoutUser(): Flow<UserState> = flow {
        var state = UserState(isLoading = true)
        emit(state)
        try {
            val result = userApi.logoutUser(userDataStore.getId())
            state = if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data
                    userDataStore.logout()
                    if (application.filesDir.isDirectory)
                        for (file in application.filesDir.listFiles()!!) {
                            file.delete()
                        }
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to fetch data.")
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