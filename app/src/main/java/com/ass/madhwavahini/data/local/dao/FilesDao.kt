package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.Files

@Dao
abstract class FilesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(files: List<Files>)

    @Query("DELETE FROM files;")
    abstract suspend fun delete()

    @Query("SELECT * FROM files WHERE catId=:catId AND SubCatId=:subCatId AND subToSubCatId=:subToSubCatId;")
    abstract suspend fun getFiles(catId: Int, subCatId: Int, subToSubCatId: Int): List<Files>

    @Query("SELECT * FROM files WHERE catId=:catId AND subCatId=:subCatId;")
    abstract suspend fun getFiles(catId: Int, subCatId: Int): List<Files>

    @Transaction
    open suspend fun insertFiles(files: List<Files>) {
        delete()
        insert(files)
    }
}