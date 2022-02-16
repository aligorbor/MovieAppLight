package ru.geekbrains.android2.movieapplight.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var name: String = "",
    var movies: MutableList<Movie> = mutableListOf(),
    var id: Int = 0,
    var isRus: Boolean = false,
    var adult:Boolean = false,
    var page: Int = 0,
    var total_pages: Int = 0,
    var total_results: Int = 0
) : Parcelable
