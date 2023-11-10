package com.ass.muktimargadarshini.di

import android.app.Application
import androidx.room.Room
import com.ass.muktimargadarshini.data.local.RoomDB
import com.ass.muktimargadarshini.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application
    ): RoomDB =
        Room.databaseBuilder(
            application,
            RoomDB::class.java,
            "muktimarga_darshini_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBannerDao(db: RoomDB): BannerDao = db.getBannerDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: RoomDB): CategoryDao = db.getCategoryDao()


    @Singleton
    @Provides
    fun provideSubCategoryDao(db: RoomDB): SubCategoryDao = db.getSubCategoryDao()

    @Singleton
    @Provides
    fun provideSubToSubCategoryDao(db: RoomDB): SubToSubCategoryDao = db.getSubToSubCategoryDao()


    @Singleton
    @Provides
    fun provideFilesDao(db: RoomDB): FilesDao = db.getFilesDao()


    @Singleton
    @Provides
    fun provideAuthorDao(db: RoomDB): AuthorDao = db.getAuthorsDao()

    @Singleton
    @Provides
    fun provideGodDao(db: RoomDB): GodDao = db.getGodsDao()

}