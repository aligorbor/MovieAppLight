package ru.geekbrains.android2.movieapplight.model.database

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteEntity")
    fun all(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE id LIKE :id")
    fun getDataById(id: Int): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoriteEntity)

    @Update
    fun update(entity: FavoriteEntity)

    @Delete
    fun delete(entity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM FavoriteEntity WHERE id = :id")
    fun countById(id: Int): Int
}