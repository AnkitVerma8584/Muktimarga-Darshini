package com.ass.muktimargadarshini.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ass.muktimargadarshini.data.local.dao.*
import com.ass.muktimargadarshini.data.local.modals.*

@Database(
    entities = [Banner::class, Category::class, SubCategory::class, SubToSubCategory::class, Files::class, Author::class, God::class],
    exportSchema = false,
    version = 2
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getSubToSubCategoryDao(): SubToSubCategoryDao
    abstract fun getFilesDao(): FilesDao
    abstract fun getAuthorsDao(): AuthorDao
    abstract fun getGodsDao(): GodDao
}