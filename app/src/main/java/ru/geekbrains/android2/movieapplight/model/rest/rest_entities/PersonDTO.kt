package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class PersonDTO(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for")
    val known_for: Array<MovieDTO?>,
    @SerializedName("known_for_department")
    val known_for_department: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profile_path: String?
)

