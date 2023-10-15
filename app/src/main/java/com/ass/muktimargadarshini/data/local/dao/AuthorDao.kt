package com.ass.muktimargadarshini.data.local.dao

import androidx.room.*
import com.ass.muktimargadarshini.data.local.modals.Author

@Dao
abstract class AuthorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(authors: List<Author>)

    @Query("SELECT COUNT(*) FROM author;")
    abstract fun getAuthorCount(): Int

    @Query("DELETE FROM author;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM author;")
    abstract suspend fun getAuthors(): List<Author>

    @Transaction
    open suspend fun insertAuthors(authors: List<Author>) {
        delete()
        insert(authors)
    }
}