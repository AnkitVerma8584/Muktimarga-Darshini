package com.ass.madhwavahini.di

import android.app.Application
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.local.dao.*
import com.ass.madhwavahini.data.remote.apis.*
import com.ass.madhwavahini.data.repository.AradhnaRepositoryImpl
import com.ass.madhwavahini.data.repository.DocumentRepositoryImpl
import com.ass.madhwavahini.data.repository.FilesRepositoryImpl
import com.ass.madhwavahini.data.repository.HomeRepositoryImpl
import com.ass.madhwavahini.data.repository.LoginRepositoryImpl
import com.ass.madhwavahini.data.repository.PanchangaRepositoryImpl
import com.ass.madhwavahini.data.repository.PaymentRepositoryImpl
import com.ass.madhwavahini.data.repository.SubCategoryRepositoryImpl
import com.ass.madhwavahini.data.repository.SubToSubCategoryRepositoryImpl
import com.ass.madhwavahini.data.repository.TranslatorRepositoryImpl
import com.ass.madhwavahini.data.repository.UserRepositoryImpl
import com.ass.madhwavahini.domain.repository.AradhnaRepository
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.repository.FilesRepository
import com.ass.madhwavahini.domain.repository.HomeRepository
import com.ass.madhwavahini.domain.repository.LoginRepository
import com.ass.madhwavahini.domain.repository.PanchangaRepository
import com.ass.madhwavahini.domain.repository.PaymentRepository
import com.ass.madhwavahini.domain.repository.SubCategoryRepository
import com.ass.madhwavahini.domain.repository.SubToSubCategoryRepository
import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.util.notification.NotificationHelper
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
    fun provideLoginRepository(
        loginApi: LoginApi,
        userDataStore: UserDataStore,
        notificationHelper: NotificationHelper
    ): LoginRepository =
        LoginRepositoryImpl(loginApi, userDataStore, notificationHelper)

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        application: Application,
        userApi: UserApi,
        userDataStore: UserDataStore,
        filesDao: FilesDao
    ): UserRepository =
        UserRepositoryImpl(application, userApi, userDataStore, filesDao)


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
    fun provideAradhnaRepository(
        aradhnaApi: AradhnaApi,
        aradhnaDao: AradhnaDao
    ): AradhnaRepository =
        AradhnaRepositoryImpl(aradhnaApi, aradhnaDao)


    @Provides
    @ViewModelScoped
    fun providePanchangaRepository(
        api: PanchangaApi,
        dao: PanchangaDao
    ): PanchangaRepository =
        PanchangaRepositoryImpl(api, dao)


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


    @Provides
    @ViewModelScoped
    fun provideTranslatorRepository(
        translateApi: TranslateApi
    ): TranslatorRepository =
        TranslatorRepositoryImpl(translateApi)


}
