package ru.geekbrains.android2.movieapplight.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: Array<MovieDTO?>,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("total_results")
    val total_results: Int?
)

