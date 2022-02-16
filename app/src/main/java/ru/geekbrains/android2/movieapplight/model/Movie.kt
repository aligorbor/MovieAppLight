package ru.geekbrains.android2.movieapplight.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    var budget: Long = 0,
    var genres: String = "",
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: Int = 0,
    val original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val status: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    var vote_count: Int = 0,
    var runtime: Int = 0,
    var revenue: Long = 0,
    var isRus: Boolean = false,
    var note: String = "",
    var isFavorite: Boolean = false
) : Parcelable


