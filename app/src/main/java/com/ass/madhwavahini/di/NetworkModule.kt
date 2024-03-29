package com.ass.madhwavahini.di

import android.app.Application
import android.provider.Settings
import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.data.remote.apis.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getOkHttpClient(
        application: Application
    ): OkHttpClient.Builder {
        val deviceId =
            Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor {
            val request =
                it.request().newBuilder()
                    .addHeader("device_id", deviceId)
                    .build()
            it.proceed(request)
        })
        return httpClient
    }

    @Provides
    @Singleton
    @MukitimargaDarshini
    fun provideRetrofitInstance(
        client: OkHttpClient.Builder
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()


    @Provides
    @Singleton
    fun provideUserDao(@MukitimargaDarshini retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideLoginDao(@MukitimargaDarshini retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)

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
    fun provideTranslationDao(): TranslateApi = Retrofit.Builder()
        .baseUrl(Api.TRANSLATION_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TranslateApi::class.java)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MukitimargaDarshini
