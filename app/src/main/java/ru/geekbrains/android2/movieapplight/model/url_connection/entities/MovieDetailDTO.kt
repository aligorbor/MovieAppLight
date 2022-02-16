package ru.geekbrains.android2.movieapplight.model.url_connection.entities

data class MovieDetailDTO(
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Long?,
    val genres: Array<GenreDTO?>,
    val homepage: String?,
    val id: Int?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val status: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)
