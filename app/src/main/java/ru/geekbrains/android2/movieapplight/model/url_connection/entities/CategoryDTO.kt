package ru.geekbrains.android2.movieapplight.model.url_connection.entities

data class CategoryDTO(
    val page: Int?,
    val results: Array<MovieDTO?>,
    val total_pages: Int?,
    val total_results: Int?
)

