package ru.geekbrains.android2.movieapplight.model

import ru.geekbrains.android2.movieapplight.model.rest.endPointImageBack
import ru.geekbrains.android2.movieapplight.model.rest.personPopular
import ru.geekbrains.android2.movieapplight.model.rest.rest_entities.*
import ru.geekbrains.android2.movieapplight.model.url_connection.ImageNotFound
import ru.geekbrains.android2.movieapplight.model.url_connection.endPointImage
import ru.geekbrains.android2.movieapplight.model.url_connection.imageLink

fun toMovies(categoryDTO: CategoryDTO?): MutableList<Movie> {
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

fun toCategory(categoryDTO: CategoryDTO?): Category {
    val movies: MutableList<Movie> = mutableListOf()
    categoryDTO?.let {
        for (result in categoryDTO.results) {
            movies.add(
                Movie(
                    adult = result?.adult ?: false,
                    backdrop_path = "$imageLink$endPointImageBack${result?.backdrop_path ?: ImageNotFound}",
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
    return Category(
        movies = movies,
        page = categoryDTO?.page ?: 0,
        total_pages = categoryDTO?.total_pages ?: 0,
        total_results = categoryDTO?.total_results ?: 0,
    )
}


fun toMovieDetail(movie: Movie, movieDetailDTO: MovieDetailDTO?): Movie {
    movie.original_title = movieDetailDTO?.original_title ?: ""
    movie.overview = movieDetailDTO?.overview ?: ""
    movie.vote_count = movieDetailDTO?.vote_count ?: 0

    movie.budget = movieDetailDTO?.budget ?: 0
    movie.revenue = movieDetailDTO?.revenue ?: 0
    movie.runtime = movieDetailDTO?.runtime ?: 0
    movieDetailDTO?.let {
        movie.genres = ""
        for (genre in movieDetailDTO.genres) {
            movie.genres = "${movie.genres}${genre?.name} "
        }
    }
    return movie
}

fun toPersons(personsDTO: PersonsDTO?): Persons {
    val personList: MutableList<Person> = mutableListOf()
    personsDTO?.let {
        for (result in personsDTO.results) {
            personList.add(
                Person(
                    adult = result?.adult ?: false,
                    gender = result?.gender ?: 0,
                    id = result?.id ?: 0,
                    known_for = toMovies(CategoryDTO(1, result?.known_for ?: arrayOf(), 1, 0)),
                    known_for_department = result?.known_for_department ?: "",
                    name = result?.name ?: "",
                    popularity = result?.popularity ?: 0.0,
                    profile_path = "$imageLink$endPointImage${result?.profile_path ?: ImageNotFound}"
                )
            )
        }
    }
    return Persons(
        name = personPopular,
        persons = personList,
        id = 0,
        page = personsDTO?.page ?: 0,
        total_pages = personsDTO?.total_pages ?: 0,
        total_results = personsDTO?.total_results ?: 0,
    )
}

fun toPersonDetail(person: Person, personDetailDTO: PersonDetailDTO?): Person {
    person.adult = personDetailDTO?.adult ?: false
    person.biography = personDetailDTO?.biography ?: ""
    person.birthday = personDetailDTO?.birthday ?: ""
    person.deathday = personDetailDTO?.deathday ?: ""
    person.gender = personDetailDTO?.gender ?: 0
    person.homepage = personDetailDTO?.homepage ?: ""
    person.id = personDetailDTO?.id ?: 0
    person.imdb_id = personDetailDTO?.imdb_id ?: ""
    person.known_for_department = personDetailDTO?.known_for_department ?: ""
    person.name = personDetailDTO?.name ?: ""
    person.place_of_birth = personDetailDTO?.place_of_birth ?: ""
    person.popularity = personDetailDTO?.popularity ?: 0.0
    return person
}

fun toGenres(genresDTO: GenresDTO?): MutableList<Genre> {
    val listOfGenres = mutableListOf<Genre>()
    genresDTO?.let {
        for (genre in genresDTO.genres) {
            listOfGenres.add(
                Genre(
                    id = genre?.id ?: 0,
                    name = genre?.name ?: ""
                )
            )
        }
    }
    return listOfGenres
}
