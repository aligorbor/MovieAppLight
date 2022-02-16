package ru.geekbrains.android2.movieapplight.model.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.geekbrains.android2.movieapplight.model.rest.rest_entities.*

interface BackendAPI {
    @GET(endPointCategory)
    fun getCategory(
        @Path("category") categoryName: String,
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String,
        @Query(paramPage) page: Int
    ): Call<CategoryDTO>

    @GET(endPointMovie)
    fun getMovieDetail(
        @Path("movieId") id: Int,
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String
    ): Call<MovieDetailDTO>

    @GET(endPointGenre)
    fun getGenres(
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String
    ): Call<GenresDTO>

    @GET(endPointDiscoverMovie)
    fun getMoviesByGenre(
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String,
        @Query(paramSortBy) sortBy: String,
        @Query(paramAdult) adult: Boolean,
        @Query(paramGenres) idGenre: Int,
        @Query(paramPage) page: Int
    ): Call<CategoryDTO>

    @GET(endPointPerson)
    fun getPerson(
        @Path("category") categoryName: String,
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String,
        @Query(paramPage) page: Int
    ): Call<PersonsDTO>

    @GET(endPointPerson)
    fun getPersonDetail(
        @Path("category") id: Int,
        @Query(paramApi_key) api_key: String,
        @Query(paramLanguage) language: String
    ): Call<PersonDetailDTO>
}