package com.ass.madhwavahini.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ass.madhwavahini.data.local.modals.Panchanga

@Dao
abstract class PanchangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(panchanga: Panchanga)

    @Query("DELETE FROM panchanga;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM panchanga;")
    abstract suspend fun getPanchanga(): Panchanga?

    @Transaction
    open suspend fun insertPanchanga(panchanga: Panchanga) {
        delete()
        insert(panchanga)
    }
}