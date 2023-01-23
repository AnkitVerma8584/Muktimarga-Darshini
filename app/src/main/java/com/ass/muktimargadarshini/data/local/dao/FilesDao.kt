package com.ass.muktimargadarshini.data.local.dao

import androidx.room.*
import com.ass.muktimargadarshini.data.local.modals.Files

@Dao
abstract class FilesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(files: List<Files>)

    @Query("DELETE FROM files;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM files WHERE cat_id=:cat_id AND sub_cat_id=:sub_cat_id AND sub_to_sub_cat_id=:sub_to_sub_cat_id;")
    abstract suspend fun getFiles(cat_id: Int, sub_cat_id: Int, sub_to_sub_cat_id: Int): List<Files>

    @Query("SELECT * FROM files WHERE cat_id=:cat_id AND sub_cat_id=:sub_cat_id;")
    abstract suspend fun getFiles(cat_id: Int, sub_cat_id: Int): List<Files>

    @Query("SELECT * FROM files WHERE files.id=:id;")
    abstract suspend fun getFileById(id: Int): Files

    @Query("SELECT COUNT(*) FROM files WHERE cat_id=:cat_id AND sub_cat_id=:sub_cat_id AND sub_to_sub_cat_id=:sub_to_sub_cat_id;")
    abstract suspend fun getFileCount(cat_id: Int, sub_cat_id: Int, sub_to_sub_cat_id: Int): Int

    @Query("SELECT COUNT(*) FROM files WHERE cat_id=:cat_id AND sub_cat_id=:sub_cat_id;")
    abstract suspend fun getFileCount(cat_id: Int, sub_cat_id: Int): Int

    @Transaction
    open suspend fun insertFiles(files: List<Files>) {
        delete()
        insert(files)
    }
}