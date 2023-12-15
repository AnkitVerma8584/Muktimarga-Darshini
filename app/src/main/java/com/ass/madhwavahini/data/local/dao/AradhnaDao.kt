package com.ass.madhwavahini.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ass.madhwavahini.data.local.modals.Aradhna

@Dao
abstract class AradhnaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(aradhnas: List<Aradhna>)

    @Query("DELETE FROM aradhna;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM aradhna;")
    abstract suspend fun getAradhnas(): List<Aradhna>

    @Transaction
    open suspend fun insertAradhnas(aradnas: List<Aradhna>) {
        delete()
        insert(aradnas)
    }
}