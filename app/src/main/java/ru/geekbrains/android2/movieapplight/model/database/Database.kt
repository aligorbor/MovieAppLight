package ru.geekbrains.android2.movieapplight.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.geekbrains.android2.movieapplight.App

@androidx.room.Database(
    entities = [
        HistoryEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DB_NAME = "add_database.db"
        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }
}