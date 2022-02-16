package ru.geekbrains.android2.movieapplight.viewmodel

import ru.geekbrains.android2.movieapplight.model.Movie

sealed class AppStateDetails {
    data class Success(val movie: Movie) : AppStateDetails()
    data class Error(val error: Throwable) : AppStateDetails()
    object Loading : AppStateDetails()
}


