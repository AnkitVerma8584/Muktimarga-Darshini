package com.ass.madhwavahini.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ass.madhwavahini.data.local.dao.AradhnaDao
import com.ass.madhwavahini.data.local.dao.GalleryDao
import com.ass.madhwavahini.data.local.dao.CategoryDao
import com.ass.madhwavahini.data.local.dao.FilesDao
import com.ass.madhwavahini.data.local.dao.PanchangaDao
import com.ass.madhwavahini.data.local.dao.SubCategoryDao
import com.ass.madhwavahini.data.local.dao.SubToSubCategoryDao
import com.ass.madhwavahini.data.local.modals.Aradhna
import com.ass.madhwavahini.data.local.modals.Gallery
import com.ass.madhwavahini.data.local.modals.Category
import com.ass.madhwavahini.data.local.modals.Files
import com.ass.madhwavahini.data.local.modals.Panchanga
import com.ass.madhwavahini.data.local.modals.SubCategory
import com.ass.madhwavahini.data.local.modals.SubToSubCategory

@Database(
    entities = [Gallery::class, Category::class, Aradhna::class, Panchanga::class, SubCategory::class, SubToSubCategory::class, Files::class],
    exportSchema = false,
    version = 4  //TODO keep 4
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getGalleryDao(): GalleryDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getAradhnaDao(): AradhnaDao
    abstract fun getPanchangaDao(): PanchangaDao
    abstract fun getSubCategoryDao(): SubCategoryDao
    abstract fun getSubToSubCategoryDao(): SubToSubCategoryDao
    abstract fun getFilesDao(): FilesDao
}