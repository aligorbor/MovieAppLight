package ru.geekbrains.android2.movieapplight.viewmodel

import ru.geekbrains.android2.movieapplight.model.Category

sealed class AppState {
    data class Success(val categoryData: List<Category>) : AppState()
    data class SuccessCategory(val category: Category) : AppState()
    data class SuccessCategoryById(val category: Category) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

