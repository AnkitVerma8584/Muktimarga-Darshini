package com.ass.muktimargadarshini.di
import com.ass.muktimargadarshini.data.remote.Api
import com.ass.muktimargadarshini.data.remote.apis.FileDataApi
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
    @KalidasaRetrofitBuild
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHomeDao(@KalidasaRetrofitBuild retrofit: Retrofit): HomeApi =
        retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideSubCategoryDao(@KalidasaRetrofitBuild retrofit: Retrofit): SubCategoryApi =
        retrofit.create(SubCategoryApi::class.java)

    @Provides
    @Singleton
    fun provideSubToSubCategoryDao(@KalidasaRetrofitBuild retrofit: Retrofit): SubToSubCategoryApi =
        retrofit.create(SubToSubCategoryApi::class.java)

    @Provides
    @Singleton
    fun provideFilesDao(@KalidasaRetrofitBuild retrofit: Retrofit): FilesApi =
        retrofit.create(FilesApi::class.java)

    @Provides
    @Singleton
    fun provideFileDataDao(@KalidasaRetrofitBuild retrofit: Retrofit): FileDataApi =
        retrofit.create(FileDataApi::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class KalidasaRetrofitBuild
