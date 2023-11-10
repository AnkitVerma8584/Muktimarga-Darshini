package com.ass.muktimargadarshini.di

import android.app.Application
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.data.local.dao.*
import com.ass.muktimargadarshini.data.remote.apis.*
import com.ass.muktimargadarshini.data.repository.DocumentRepositoryImpl
import com.ass.muktimargadarshini.data.repository.FilesRepositoryImpl
import com.ass.muktimargadarshini.data.repository.HomeRepositoryImpl
import com.ass.muktimargadarshini.data.repository.PaymentRepositoryImpl
import com.ass.muktimargadarshini.data.repository.SubCategoryRepositoryImpl
import com.ass.muktimargadarshini.data.repository.SubToSubCategoryRepositoryImpl
import com.ass.muktimargadarshini.data.repository.UserRepositoryImpl
import com.ass.muktimargadarshini.domain.repository.DocumentRepository
import com.ass.muktimargadarshini.domain.repository.FilesRepository
import com.ass.muktimargadarshini.domain.repository.HomeRepository
import com.ass.muktimargadarshini.domain.repository.PaymentRepository
import com.ass.muktimargadarshini.domain.repository.SubCategoryRepository
import com.ass.muktimargadarshini.domain.repository.SubToSubCategoryRepository
import com.ass.muktimargadarshini.domain.repository.UserRepository
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
    fun provideSubToSubCategoryRepositoryOld(
        userDataStore: UserDataStore,
        subToSubCategoryApi: SubToSubCategoryApi,
        subToSubCategoryDao: SubToSubCategoryDao,
        filesDao: FilesDao,
        filesApi: FilesApi,
        application: Application
    ): SubToSubCategoryRepository =
        SubToSubCategoryRepositoryImpl(
            userDataStore,
            subToSubCategoryApi,
            subToSubCategoryDao,
            filesDao,
            application,
            filesApi
        )

    @Provides
    @ViewModelScoped
    fun provideFileRepository(
        filesApi: FilesApi,
        filesDao: FilesDao,
        application: Application,
        userDataStore: UserDataStore
    ): FilesRepository =
        FilesRepositoryImpl(
            userDataStore,
            filesApi,
            filesDao,
            application
        )

    @Provides
    @ViewModelScoped
    fun provideFileDataRepository(
        filesApi: FilesApi,
        application: Application
    ): DocumentRepository =
        DocumentRepositoryImpl(filesApi, application)


}
