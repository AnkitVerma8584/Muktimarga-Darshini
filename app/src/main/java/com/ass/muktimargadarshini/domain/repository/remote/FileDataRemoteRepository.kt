package com.ass.muktimargadarshini.domain.repository.remote

import com.ass.muktimargadarshini.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileDataRemoteRepository {

    fun getFileData(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<Resource<File>>
}