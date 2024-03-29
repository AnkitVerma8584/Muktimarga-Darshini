package com.ass.madhwavahini.data.local.dao

import androidx.room.*
import com.ass.madhwavahini.data.local.modals.SubToSubCategory

@Dao
abstract class SubToSubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(subToSubCategory: List<SubToSubCategory>)

    @Query("DELETE FROM sub_to_sub_category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM sub_to_sub_category WHERE sub_to_sub_category.catId=:catId AND sub_to_sub_category.subCatId=:subCatId;")
    abstract suspend fun getSubToSubCategories(catId: Int, subCatId: Int): List<SubToSubCategory>

    @Transaction
    open suspend fun insertSubToSubCategory(subToSubCategory: List<SubToSubCategory>) {
        delete()
        insert(subToSubCategory)
    }
}