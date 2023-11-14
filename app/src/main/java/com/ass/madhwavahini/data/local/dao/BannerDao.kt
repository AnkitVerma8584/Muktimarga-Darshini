package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.Banner

@Dao
abstract class BannerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(banners: List<Banner>)

    @Query("DELETE FROM banner;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM banner;")
    abstract suspend fun getBanners(): List<Banner>

    @Transaction
    open suspend fun insertBanners(banners: List<Banner>) {
        delete()
        insert(banners)
    }
}