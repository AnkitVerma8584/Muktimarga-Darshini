package com.ass.muktimargadarshini.data.local.dao

import androidx.room.*
import com.ass.muktimargadarshini.data.local.modals.Files

@Dao
abstract class FilesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(files: List<Files>)

    @Query("DELETE FROM files;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM files WHERE catId=:catId AND SubCatId=:subCatId AND subToSubCatId=:subToSubCatId;")
    abstract suspend fun getFiles(catId: Int, subCatId: Int, subToSubCatId: Int): List<Files>

    @Query("SELECT * FROM files WHERE catId=:catId AND subCatId=:subCatId;")
    abstract suspend fun getFiles(catId: Int, subCatId: Int): List<Files>

    @Query("SELECT * FROM files WHERE files.id=:id;")
    abstract suspend fun getFileById(id: Int): Files

    @Query("SELECT COUNT(*) FROM files WHERE catId=:catId AND subCatId=:subCatId AND subToSubCatId=:subToSubCatId;")
    abstract suspend fun getFileCount(catId: Int, subCatId: Int, subToSubCatId: Int): Int

    @Query("SELECT COUNT(*) FROM files WHERE catId=:catId AND subCatId=:subCatId;")
    abstract suspend fun getFileCount(catId: Int, subCatId: Int): Int

    @Transaction
    open suspend fun insertFiles(files: List<Files>) {
        delete()
        insert(files)
    }
}