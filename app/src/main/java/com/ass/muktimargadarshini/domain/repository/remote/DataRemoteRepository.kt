package com.ass.muktimargadarshini.domain.repository.remote

import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DataRemoteRepository {

    fun getAuthors(): Flow<Resource<List<HomeAuthor>>>

    fun getGods(): Flow<Resource<List<HomeGod>>>

}