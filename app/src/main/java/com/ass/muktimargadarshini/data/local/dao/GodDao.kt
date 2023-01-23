package com.ass.muktimargadarshini.data.local.dao

import androidx.room.*
import com.ass.muktimargadarshini.data.local.modals.Banner
import com.ass.muktimargadarshini.data.local.modals.God

@Dao
abstract class GodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(gods: List<God>)

    @Query("SELECT COUNT(*) FROM god;")
    abstract fun getGodCount(): Int

    @Query("DELETE FROM god;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM god;")
    abstract suspend fun getGods(): List<God>

    @Transaction
    open suspend fun insertBanners(gods: List<God>) {
        delete()
        insert(gods)
    }
}