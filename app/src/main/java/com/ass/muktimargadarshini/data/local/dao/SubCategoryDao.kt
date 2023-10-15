package com.ass.muktimargadarshini.data.local.dao

import androidx.room.*
import com.ass.muktimargadarshini.data.local.modals.SubCategory

@Dao
abstract class SubCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(subCategory: List<SubCategory>)

    @Query("DELETE FROM sub_category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM sub_category WHERE sub_category.catId=:id;")
    abstract suspend fun getSubCategories(id: Int): List<SubCategory>

    @Query("SELECT COUNT(*) FROM sub_category WHERE sub_category.catId=:id;")
    abstract suspend fun getSubCategoryCount(id: Int): Int

    @Transaction
    open suspend fun insertSubCategory(subCategory: List<SubCategory>) {
        delete()
        insert(subCategory)
    }
}