package com.ass.muktimargadarshini.data.local.repositoryImpl

import com.ass.muktimargadarshini.data.local.dao.AuthorDao
import com.ass.muktimargadarshini.data.local.dao.BannerDao
import com.ass.muktimargadarshini.data.local.dao.CategoryDao
import com.ass.muktimargadarshini.data.local.dao.GodDao
import com.ass.muktimargadarshini.data.local.mapper.*
import com.ass.muktimargadarshini.domain.modals.HomeAuthor
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.modals.HomeGod
import com.ass.muktimargadarshini.domain.repository.local.DataLocalRepository
import com.ass.muktimargadarshini.domain.repository.local.HomeLocalRepository

class DataLocalRepositoryImpl(
    private val authorDao: AuthorDao,
    private val godDao: GodDao
) :
    DataLocalRepository {

    override suspend fun hasCachedAuthors(): Boolean = authorDao.getAuthorCount() != 0

    override suspend fun getAuthors(): List<HomeAuthor> = authorDao.getAuthors().mapToHomeAuthor()

    override suspend fun hasCachedGods(): Boolean = godDao.getGodCount() != 0

    override suspend fun getGods(): List<HomeGod> = godDao.getGods().mapToHomeGod()

    override suspend fun submitAuthors(authorList: List<HomeAuthor>) {
        authorDao.insertAuthors(authors = authorList.mapToAuthor())
    }

    override suspend fun submitGods(godList: List<HomeGod>) {
        godDao.insertGods(gods = godList.mapToGod())
    }

}