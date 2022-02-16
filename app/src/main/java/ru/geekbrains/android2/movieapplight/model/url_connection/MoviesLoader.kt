package ru.geekbrains.android2.movieapplight.model.url_connection

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.geekbrains.android2.movieapplight.BuildConfig
import ru.geekbrains.android2.movieapplight.interactors.StringsInteractor
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Movie
import ru.geekbrains.android2.movieapplight.model.url_connection.entities.CategoryDTO
import ru.geekbrains.android2.movieapplight.model.url_connection.entities.MovieDetailDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val readTimeout = 10000
const val mainLink = "https://api.themoviedb.org"
const val imageLink = "https://image.tmdb.org"
const val endPointImage = "/t/p/w200"
const val ImageNotFound = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"
const val endPointNowPlaying = "/3/movie/now_playing"
const val endPointTopRated = "/3/movie/top_rated"
const val endPointUpComing = "/3/movie/upcoming"
const val endPointPopular = "/3/movie/popular"
const val endPointMovie = "/3/movie/"
const val endPointGenre = "/3/genre/movie/list"
const val paramLanguageRU = "ru-RU"
const val paramLanguageEN = "en-US"


object MoviesLoader {

    fun loadCategories(isRus: Boolean, interactor: StringsInteractor): MutableList<Category> {
        val page = 1
        val lang = if (isRus) paramLanguageRU
        else paramLanguageEN
        return mutableListOf(
            Category(
                name = interactor.strNowPlaying,
                movies = toMovies(loadEntity(endPointNowPlaying, lang, page)),
                id = 0
            ),
            Category(
                name = interactor.strPopular,
                movies = toMovies(loadEntity(endPointPopular, lang, page)),
                id = 1
            ),
            Category(
                name = interactor.strTopRated,
                movies = toMovies(loadEntity(endPointTopRated, lang, page)),
                id = 2
            ),
            Category(
                name = interactor.strUpcoming,
                movies = toMovies(loadEntity(endPointUpComing, lang, page)),
                id = 3
            )
        )
    }

    fun loadMovieDetail(movie: Movie): Movie {
        val lang = if (movie.isRus) paramLanguageRU
        else paramLanguageEN
        return toMovieDetail(movie, loadEntity("$endPointMovie${movie.id}", lang, 1))
    }

    private inline fun <reified T> loadEntity(
        endPoint: String,
        paramLanguage: String,
        page: Int
    ): T? {
        try {
            val uri =
                URL("$mainLink$endPoint?api_key=${BuildConfig.MOVIE_API_KEY}&language=$paramLanguage&page=$page")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = readTimeout
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                // преобразование ответа от сервера (JSON) в модель данных (CategoryDTO)
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, T::class.java)
            } catch (e: Exception) {
                throw e
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            throw e
        }
    }

    fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun toMovies(categoryDTO: CategoryDTO?): MutableList<Movie> {
        val movies: MutableList<Movie> = mutableListOf()
        categoryDTO?.let {
            for (result in categoryDTO.results) {
                movies.add(
                    Movie(
                        adult = result?.adult ?: false,
                        backdrop_path = result?.backdrop_path ?: "",
                        id = result?.id ?: 0,
                        original_language = result?.original_language ?: "",
                        original_title = result?.original_title ?: "",
                        overview = result?.overview ?: "",
                        popularity = result?.popularity ?: 0.0,
                        poster_path = "$imageLink$endPointImage${result?.poster_path ?: ImageNotFound}",
                        release_date = result?.release_date ?: "",
                        title = result?.title ?: "",
                        video = result?.video ?: false,
                        vote_average = result?.vote_average ?: 0.0,
                        vote_count = result?.vote_count ?: 0
                    )
                )
            }
        }
        return movies
    }

    private fun toMovieDetail(movie: Movie, movieDetailDTO: MovieDetailDTO?): Movie {
        movie.budget = movieDetailDTO?.budget ?: 0
        movie.revenue = movieDetailDTO?.revenue ?: 0
        movie.runtime = movieDetailDTO?.runtime ?: 0
        movieDetailDTO?.let {
            for (genre in movieDetailDTO.genres) {
                movie.genres = "${movie.genres}${genre?.name} "
            }
        }
        return movie
    }
}