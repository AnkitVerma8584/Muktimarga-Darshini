package com.ass.muktimargadarshini.domain.repository.remote
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRemoteRepository {

    fun getBanners(): Flow<Resource<List<String>>>

    fun getCategories(): Flow<Resource<List<HomeCategory>>>

}