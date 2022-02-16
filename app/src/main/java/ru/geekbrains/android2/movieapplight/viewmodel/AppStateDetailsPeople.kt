package ru.geekbrains.android2.movieapplight.viewmodel

import ru.geekbrains.android2.movieapplight.model.Person

sealed class AppStateDetailsPeople {
    data class Success(val people: Person) : AppStateDetailsPeople()
    data class Error(val error: Throwable) : AppStateDetailsPeople()
    object Loading : AppStateDetailsPeople()
}


