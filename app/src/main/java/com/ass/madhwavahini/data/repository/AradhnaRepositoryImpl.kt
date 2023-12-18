package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.dao.AradhnaDao
import com.ass.madhwavahini.data.local.mapper.mapToAradhnas
import com.ass.madhwavahini.data.local.mapper.mapToHomeAradhnas
import com.ass.madhwavahini.data.remote.apis.AradhnaApi
import com.ass.madhwavahini.domain.modals.HomeAradhna
import com.ass.madhwavahini.domain.repository.AradhnaRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class AradhnaRepositoryImpl(
    private val aradhnaApi: AradhnaApi,
    private val aradhnaDao: AradhnaDao
) : AradhnaRepository {

    override fun getAradhnas(): Flow<UiStateList<HomeAradhna>> = flow {
        var state = UiStateList<HomeAradhna>(isLoading = true)
        emit(state)

        val localAradhnas = aradhnaDao.getAradhnas().mapToHomeAradhnas()

        if (localAradhnas.isNotEmpty()) {
            state = state.copy(
                isLoading = false,
                data = localAradhnas
            )
            emit(state)
        }
        try {
            val result = aradhnaApi.getAradhnas()
            if (result.isSuccessful && result.body() != null && Random.nextBoolean()) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (localAradhnas != data) {
                        aradhnaDao.insertAradhnas(data.mapToAradhnas())
                    } else return@flow
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
                    error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            if (localAradhnas.isEmpty())
                state = state.copy(
                    isLoading = false,
                    error = e.getError()
                )
        } finally {
            emit(state)
        }
    }
}