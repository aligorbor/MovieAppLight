package ru.geekbrains.android2.movieapplight.model

import ru.geekbrains.android2.movieapplight.interactors.StringsInteractor

interface Repository {
    fun getCategoriesFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int
    ): List<Category>

    fun getMovieDetailFromRemoteStorage(movie: Movie): Movie

    fun getPersonsPopularFromRemoteStorage(
        isRus: Boolean,
        adult: Boolean,
        page: Int
    ): Persons

    fun getPersonDetailFromRemoteStorage(person: Person): Person

    fun getAllHistory(): List<Movie>
    fun saveToHistory(movie: Movie)
    fun getAllFavorite(): List<Movie>
    fun saveToFavorite(movie: Movie)
    fun deleteFromFavorite(id: Int)
    fun isFavorite(id: Int): Boolean
    fun getNote(id: Int): String

    fun getCategoryByIdFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int,
        id: Int
    ): Category

}