package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.FilesDao
import com.ass.muktimargadarshini.data.local.mapper.mapToFilesList
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeFiles
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeFilesList
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.local.FileLocalRepository

class FilesLocalRepositoryImpl(private val filesDao: FilesDao) :
    FileLocalRepository {

    override suspend fun getFiles(catId: Int, subCatId: Int): List<HomeFiles> =
        filesDao.getFiles(catId, subCatId).mapToHomeFilesList()

    override suspend fun getFiles(
        catId: Int,
        subCatId: Int,
        subToSubCatId: Int
    ): List<HomeFiles> =
        filesDao.getFiles(catId, subCatId, subToSubCatId).mapToHomeFilesList()

    override suspend fun getFilesCount(catId: Int, subCatId: Int): Int =
        filesDao.getFileCount(catId, subCatId)

    override suspend fun getFilesCount(catId: Int, subCatId: Int, subToSubCatId: Int): Int =
        filesDao.getFileCount(catId, subCatId, subToSubCatId)

    override suspend fun getFileById(fileId: Int): HomeFiles? =
        filesDao.getFileById(fileId).mapToHomeFiles()

    override suspend fun submitFiles(files: List<HomeFiles>) {
        filesDao.insertFiles(files.mapToFilesList())
    }
}