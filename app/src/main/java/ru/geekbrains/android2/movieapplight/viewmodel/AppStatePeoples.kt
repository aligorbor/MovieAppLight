package ru.geekbrains.android2.movieapplight.viewmodel

import ru.geekbrains.android2.movieapplight.model.Persons

sealed class AppStatePeoples {
    data class Success(val peoplesData: Persons) : AppStatePeoples()
    data class Error(val error: Throwable) : AppStatePeoples()
    object Loading : AppStatePeoples()
}

