package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.Gallery

@Dao
abstract class GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(galleries: List<Gallery>)

    @Query("DELETE FROM gallery;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM gallery;")
    abstract suspend fun getGalleryList(): List<Gallery>

    @Transaction
    open suspend fun insertGallery(galleries: List<Gallery>) {
        delete()
        insert(galleries)
    }
}