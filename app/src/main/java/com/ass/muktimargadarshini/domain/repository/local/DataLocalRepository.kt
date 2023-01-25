package com.ass.muktimargadarshini.domain.repository.local

import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeGod

interface DataLocalRepository {

    suspend fun hasCachedAuthors(): Boolean

    suspend fun getAuthors(): List<HomeAuthor>

    suspend fun hasCachedGods(): Boolean

    suspend fun getGods(): List<HomeGod>

    suspend fun submitAuthors(authorList: List<HomeAuthor>)

    suspend fun submitGods(godList: List<HomeGod>)
}