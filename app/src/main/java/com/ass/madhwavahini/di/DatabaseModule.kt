package com.ass.madhwavahini.di

import android.app.Application
import androidx.room.Room
import com.ass.madhwavahini.data.local.RoomDB
import com.ass.madhwavahini.data.local.dao.*
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
    fun provideGalleryDao(db: RoomDB): GalleryDao = db.getGalleryDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: RoomDB): CategoryDao = db.getCategoryDao()

    @Singleton
    @Provides
    fun provideAradhnaDao(db: RoomDB): AradhnaDao = db.getAradhnaDao()

    @Singleton
    @Provides
    fun providePanchangaDao(db: RoomDB): PanchangaDao = db.getPanchangaDao()

    @Singleton
    @Provides
    fun provideSubCategoryDao(db: RoomDB): SubCategoryDao = db.getSubCategoryDao()

    @Singleton
    @Provides
    fun provideSubToSubCategoryDao(db: RoomDB): SubToSubCategoryDao = db.getSubToSubCategoryDao()


    @Singleton
    @Provides
    fun provideFilesDao(db: RoomDB): FilesDao = db.getFilesDao()


}