package com.ass.muktimargadarshini.di

import android.app.Application
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.local.dao.*
import com.ass.muktimargadarshini.data.local.repositoryImpl.*
import com.ass.muktimargadarshini.data.remote.apis.*
import com.ass.muktimargadarshini.data.remote.repository.*
import com.ass.muktimargadarshini.data.repository.HomeRepositoryImpl
import com.ass.muktimargadarshini.data.repository.PaymentRepositoryImpl
import com.ass.muktimargadarshini.data.repository.SubCategoryRepositoryImpl
import com.ass.muktimargadarshini.data.repository.SubToSubCategoryRepositoryImpl
import com.ass.muktimargadarshini.data.repository.UserRepositoryImpl
import com.ass.muktimargadarshini.domain.repository.HomeRepository
import com.ass.muktimargadarshini.domain.repository.PaymentRepository
import com.ass.muktimargadarshini.domain.repository.SubCategoryRepository
import com.ass.muktimargadarshini.domain.repository.SubToSubCategoryRepository
import com.ass.muktimargadarshini.domain.repository.UserRepository
import com.ass.muktimargadarshini.domain.repository.local.*
import com.ass.muktimargadarshini.domain.repository.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        userApi: UserApi,
        userDataStore: UserDataStore
    ): UserRepository =
        UserRepositoryImpl(userApi, userDataStore)


    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        bannerDao: BannerDao,
        categoryDao: CategoryDao,
        homeApi: HomeApi
    ): HomeRepository =
        HomeRepositoryImpl(bannerDao, categoryDao, homeApi)


    @Provides
    @ViewModelScoped
    fun providePaymentRepository(
        paymentApi: PaymentApi,
        userDataStore: UserDataStore
    ): PaymentRepository =
        PaymentRepositoryImpl(
            paymentApi = paymentApi,
            userDataStore = userDataStore
        )

    @Provides
    @ViewModelScoped
    fun provideSubCategoryRepository(
        subCategoryApi: SubCategoryApi,
        subCategoryDao: SubCategoryDao
    ): SubCategoryRepository =
        SubCategoryRepositoryImpl(subCategoryApi, subCategoryDao)


    @Provides
    @ViewModelScoped
    fun provideSubToSubCategoryLocalRepository(
        subToSubCategoryDao: SubToSubCategoryDao
    ): SubToSubCategoryLocalRepository =
        SubToSubCategoryLocalRepositoryImpl(subToSubCategoryDao)

    @Provides
    @ViewModelScoped
    fun provideFilesLocalRepository(
        filesDao: FilesDao
    ): FileLocalRepository =
        FilesLocalRepositoryImpl(filesDao)

    @Provides
    @ViewModelScoped
    fun provideSubToSubCategoryRepositoryOld(
        subToSubCategoryApi: SubToSubCategoryApi,
        subToSubCategoryLocalRepository: SubToSubCategoryLocalRepository,
        fileLocalRepository: FileLocalRepository,
        filesDataApi: FileDataApi,
        application: Application
    ): SubToSubCategoryRemoteRepository =
        SubToSubCategoryRemoteRepositoryImpl(
            subToSubCategoryApi,
            subToSubCategoryLocalRepository,
            fileLocalRepository,
            application, filesDataApi
        )

    @Provides
    @ViewModelScoped
    fun provideFileRepository(
        filesApi: FilesApi,
        fileLocalRepository: FileLocalRepository,
        filesDataApi: FileDataApi,
        application: Application
    ): FilesRemoteRepository =
        FilesRemoteRepositoryImpl(filesApi, fileLocalRepository, application, filesDataApi)

    @Provides
    @ViewModelScoped
    fun provideFileDataRepository(
        filesApi: FileDataApi,
        application: Application
    ): FileDataRemoteRepository =
        FileDataRemoteRepositoryImpl(filesApi, application)

    @Provides
    @ViewModelScoped
    fun provideDataLocalRepository(
        authorDao: AuthorDao,
        godDao: GodDao
    ): DataLocalRepository =
        DataLocalRepositoryImpl(authorDao, godDao)

    @Provides
    @ViewModelScoped
    fun provideDataRepository(
        dataApi: DataApi,
        dataLocalRepository: DataLocalRepository
    ): DataRemoteRepository =
        DataRemoteRepositoryImpl(dataApi, dataLocalRepository)

    @Provides
    @ViewModelScoped
    fun provideSubToSubCategoryRepository(
        subToSubCategoryApi: SubToSubCategoryApi,
        subToSubCategoryDao: SubToSubCategoryDao,
        filesDataApi: FileDataApi,
        filesDao: FilesDao,
        dataApi: DataApi,
        godDao: GodDao,
        authorDao: AuthorDao,
        application: Application
    ): SubToSubCategoryRepository =
        SubToSubCategoryRepositoryImpl(
            application,
            subToSubCategoryApi,
            subToSubCategoryDao,
            filesDao,
            filesDataApi,
            dataApi,
            godDao, authorDao
        )


}
