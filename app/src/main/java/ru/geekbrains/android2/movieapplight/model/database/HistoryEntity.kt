package ru.geekbrains.android2.movieapplight.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val backdrop_path: String,
    val note: String
)

