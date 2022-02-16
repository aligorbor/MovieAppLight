package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class PersonsDTO(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: Array<PersonDTO?>,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("total_results")
    val total_results: Int?
)

