package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.Category

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(category: List<Category>)

    @Query("DELETE FROM category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM category;")
    abstract suspend fun getCategories(): List<Category>

    @Transaction
    open suspend fun insertCategory(category: List<Category>) {
        delete()
        insert(category)
    }
}