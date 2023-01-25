package com.ass.muktimargadarshini.data.remote.repository

import com.ass.muktimargadarshini.data.remote.apis.DataApi
import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.domain.repository.local.DataLocalRepository
import com.ass.muktimargadarshini.domain.repository.remote.DataRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.domain.utils.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class DataRemoteRepositoryImpl(
    private val dataApi: DataApi,
    private val dataLocalRepository: DataLocalRepository
) : DataRemoteRepository {

    override fun getAuthors(): Flow<Resource<List<HomeAuthor>>> = flow {
        emit(Resource.Loading)
        try {
            if (dataLocalRepository.hasCachedAuthors())
                emit(Resource.Cached(dataLocalRepository.getAuthors()))
            val result = dataApi.getAuthor()
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    dataLocalRepository.submitAuthors(data)
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Failure(StringUtil.DynamicText(result.body()!!.message)))
                }
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }

    override fun getGods(): Flow<Resource<List<HomeGod>>> = flow {
        emit(Resource.Loading)
        try {
            if (dataLocalRepository.hasCachedAuthors())
                emit(Resource.Cached(dataLocalRepository.getGods()))
            val result = dataApi.getGods()
            if (result.isSuccessful && result.body() != null) {
                if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    dataLocalRepository.submitGods(data)
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Failure(StringUtil.DynamicText(result.body()!!.message)))
                }
            } else {
                emit(Resource.Failure(StringUtil.DynamicText("Unable to fetch data")))
            }
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    if (e is IOException)
                        StringUtil.DynamicText("Please check your internet connection")
                    else StringUtil.DynamicText(e.localizedMessage ?: "Some server error occurred")
                )
            )
        }
    }
}