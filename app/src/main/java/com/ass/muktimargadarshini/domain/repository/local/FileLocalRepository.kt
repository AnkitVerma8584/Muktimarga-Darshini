package com.ass.muktimargadarshini.domain.repository.local

import com.ass.muktimargadarshini.domain.modals.HomeFiles

interface FileLocalRepository {

    suspend fun getFiles(catId: Int, subCatId: Int): List<HomeFiles>

    suspend fun getFiles(catId: Int, subCatId: Int, subToSubCatId: Int): List<HomeFiles>

    suspend fun getFilesCount(catId: Int, subCatId: Int): Int

    suspend fun getFilesCount(catId: Int, subCatId: Int, subToSubCatId: Int): Int

    suspend fun getFileById(fileId: Int): HomeFiles?

    suspend fun submitFiles(files: List<HomeFiles>)
}