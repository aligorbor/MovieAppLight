package ru.geekbrains.android2.movieapplight.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var adult: Boolean = false,
    var also_known_as: MutableList<String> = mutableListOf(),
    var biography: String = "",
    var birthday: String = "",
    var deathday: String = "",
    var gender: Int = 0,
    var homepage: String = "",
    var id: Int = 0,
    var imdb_id: String = "",
    var known_for_department: String = "",
    var name: String = "",
    var place_of_birth: String = "",
    var popularity: Double = 0.0,
    var profile_path: String = "",
    var known_for: MutableList<Movie> = mutableListOf(),
    var isRus: Boolean = false
) : Parcelable

