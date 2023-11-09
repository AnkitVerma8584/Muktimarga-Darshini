package com.ass.muktimargadarshini.di

import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.data.remote.apis.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @MukitimargaDarshini
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideUserDao(@MukitimargaDarshini retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideHomeDao(@MukitimargaDarshini retrofit: Retrofit): HomeApi =
        retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun providePaymentDao(@MukitimargaDarshini retrofit: Retrofit): PaymentApi =
        retrofit.create(PaymentApi::class.java)

    @Provides
    @Singleton
    fun provideSubCategoryDao(@MukitimargaDarshini retrofit: Retrofit): SubCategoryApi =
        retrofit.create(SubCategoryApi::class.java)

    @Provides
    @Singleton
    fun provideSubToSubCategoryDao(@MukitimargaDarshini retrofit: Retrofit): SubToSubCategoryApi =
        retrofit.create(SubToSubCategoryApi::class.java)

    @Provides
    @Singleton
    fun provideFilesDao(@MukitimargaDarshini retrofit: Retrofit): FilesApi =
        retrofit.create(FilesApi::class.java)

    @Provides
    @Singleton
    fun provideFileDataDao(@MukitimargaDarshini retrofit: Retrofit): FileDataApi =
        retrofit.create(FileDataApi::class.java)

    @Provides
    @Singleton
    fun provideDataDao(@MukitimargaDarshini retrofit: Retrofit): DataApi =
        retrofit.create(DataApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MukitimargaDarshini
