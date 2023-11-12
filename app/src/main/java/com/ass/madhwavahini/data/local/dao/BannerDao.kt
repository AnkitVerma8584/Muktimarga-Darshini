package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.Banner

@Dao
abstract class BannerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(banners: List<Banner>)

    @Query("SELECT COUNT(*) FROM banner;")
    abstract fun getBannerCount(): Int

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