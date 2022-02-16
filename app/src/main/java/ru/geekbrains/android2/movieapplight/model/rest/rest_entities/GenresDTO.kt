package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class GenresDTO(
    @SerializedName("genres")
    val genres: Array<GenreDTO?>
)

