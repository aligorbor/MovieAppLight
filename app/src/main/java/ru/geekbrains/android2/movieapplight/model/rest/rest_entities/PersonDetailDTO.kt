package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class PersonDetailDTO(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("also_known_as")
    val also_known_as: Array<String?>,
    @SerializedName("biography")
    val biography: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdb_id: String?,
    @SerializedName("known_for_department")
    val known_for_department: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("place_of_birth")
    val place_of_birth: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profile_path: String?
)

