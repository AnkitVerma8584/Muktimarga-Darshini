package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.FilesDao
import com.ass.muktimargadarshini.data.local.mapper.mapToFilesList
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeFiles
import com.ass.muktimargadarshini.data.local.mapper.mapToHomeFilesList
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.local.FileLocalRepository

class FilesLocalRepositoryImpl(private val filesDao: FilesDao) :
    FileLocalRepository {

    override suspend fun getFiles(cat_id: Int, sub_cat_id: Int): List<HomeFiles> =
        filesDao.getFiles(cat_id, sub_cat_id).mapToHomeFilesList()

    override suspend fun getFiles(
        cat_id: Int,
        sub_cat_id: Int,
        sub_to_sub_cat_id: Int
    ): List<HomeFiles> =
        filesDao.getFiles(cat_id, sub_cat_id, sub_to_sub_cat_id).mapToHomeFilesList()

    override suspend fun getFilesCount(cat_id: Int, sub_cat_id: Int): Int =
        filesDao.getFileCount(cat_id, sub_cat_id)

    override suspend fun getFilesCount(cat_id: Int, sub_cat_id: Int, sub_to_sub_cat_id: Int): Int =
        filesDao.getFileCount(cat_id, sub_cat_id, sub_to_sub_cat_id)

    override suspend fun getFileById(fileId: Int): HomeFiles? =
        filesDao.getFileById(fileId).mapToHomeFiles()

    override suspend fun submitFiles(files: List<HomeFiles>) {
        filesDao.insertFiles(files.mapToFilesList())
    }
}