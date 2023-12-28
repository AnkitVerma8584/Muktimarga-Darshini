package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.remote.apis.HomeApi
import com.ass.madhwavahini.domain.repository.HomeRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val homeApi: HomeApi
) : HomeRepository {

    override fun getQuoteState(): Flow<UiState<String>> = flow {
        var state = UiState<String>(isLoading = true)
        emit(state)

        val localQuote = userDataStore.getQuote()

        if (localQuote.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localQuote)
            emit(state)
        }

        try {
            val result = homeApi.getDailyQuote()
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (localQuote != data)
                        userDataStore.saveQuote(data)
                    else return@flow
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state =
                    state.copy(
                        isLoading = false,
                        error = StringUtil.DynamicText("Unable to fetch")
                    )
            }
        } catch (e: Exception) {
            if (localQuote.isBlank())
                state = state.copy(
                    isLoading = false,
                    error = e.getError()
                )
        } finally {
            emit(state)
        }
    }
}