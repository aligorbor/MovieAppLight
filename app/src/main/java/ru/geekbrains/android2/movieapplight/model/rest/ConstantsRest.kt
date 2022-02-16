package ru.geekbrains.android2.movieapplight.model.rest

const val baseUrlMainPart = "https://api.themoviedb.org/"
const val baseUrlVersion = "3/"

const val endPointCategory = "movie/{category}"
const val categoryNowPlaying = "now_playing"
const val categoryTopRated = "top_rated"
const val categoryUpComing = "upcoming"
const val categoryPopular = "popular"

const val endPointDiscoverMovie = "discover/movie"
const val endPointMovie = "movie/{movieId}"
const val endPointGenre = "genre/movie/list"

const val paramApi_key = "api_key"
const val paramLanguage = "language"
const val paramPage = "page"
const val paramAdult = "include_adult"
const val paramGenres = "with_genres"
const val paramSortBy = "sort_by"

const val sortPopularityDesc = "popularity.desc"
const val languageRU = "ru-RU"
const val languageEN = "en-US"
const val imageLink = "https://image.tmdb.org"
const val endPointImage = "/t/p/w200"
const val endPointImageBack = "/t/p/w300"
const val ImageNotFound = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"

const val constCategoryCount = 4

const val endPointPerson = "person/{category}"
const val personPopular = "popular"