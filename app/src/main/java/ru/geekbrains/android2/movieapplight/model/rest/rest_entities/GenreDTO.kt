package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

