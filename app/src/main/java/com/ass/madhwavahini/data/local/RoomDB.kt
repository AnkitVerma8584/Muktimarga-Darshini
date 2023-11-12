package com.ass.madhwavahini.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ass.madhwavahini.data.local.dao.BannerDao
import com.ass.madhwavahini.data.local.dao.CategoryDao
import com.ass.madhwavahini.data.local.dao.FilesDao
import com.ass.madhwavahini.data.local.dao.SubCategoryDao
import com.ass.madhwavahini.data.local.dao.SubToSubCategoryDao
import com.ass.madhwavahini.data.local.modals.Banner
import com.ass.madhwavahini.data.local.modals.Category
import com.ass.madhwavahini.data.local.modals.Files
import com.ass.madhwavahini.data.local.modals.SubCategory
import com.ass.madhwavahini.data.local.modals.SubToSubCategory

@Database(
    entities = [Banner::class, Category::class, SubCategory::class, SubToSubCategory::class, Files::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getSubToSubCategoryDao(): SubToSubCategoryDao
    abstract fun getFilesDao(): FilesDao
}