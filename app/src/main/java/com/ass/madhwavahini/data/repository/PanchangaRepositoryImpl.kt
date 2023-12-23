package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.dao.PanchangaDao
import com.ass.madhwavahini.data.local.mapper.mapToHomePanchanga
import com.ass.madhwavahini.data.local.mapper.mapToPanchanga
import com.ass.madhwavahini.data.remote.apis.PanchangaApi
import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.domain.repository.PanchangaRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PanchangaRepositoryImpl(
    private val panchangaApi: PanchangaApi, private val panchangaDao: PanchangaDao
) : PanchangaRepository {

    override fun getPanchanga(): Flow<UiState<HomePanchanga>> = flow {
        var state = UiState<HomePanchanga>(isLoading = true)
        emit(state)

        val localPanchanga = panchangaDao.getPanchanga()?.mapToHomePanchanga()

        if (localPanchanga != null) {
            state = state.copy(
                isLoading = false, data = localPanchanga
            )
            emit(state)
        }
        try {
            val result = panchangaApi.getPanchanga()
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (data != localPanchanga) {
                        panchangaDao.insertPanchanga(data.mapToPanchanga())
                    } else return@flow
                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            if (localPanchanga != null)
                state = state.copy(
                    isLoading = false, error = e.getError()
                )
        } finally {
            emit(state)
        }
    }
}