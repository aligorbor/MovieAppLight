package ru.geekbrains.android2.movieapplight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.geekbrains.android2.movieapplight.interactors.StringsInteractor
import ru.geekbrains.android2.movieapplight.model.Category
import ru.geekbrains.android2.movieapplight.model.Movie
import ru.geekbrains.android2.movieapplight.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getCategoriesFromRemoteSource(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int
    ) =
        getDataFromRemoteSource(isRus, interactor, adult, page)

    fun getCategoryByIdFromRemoteSource(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int,
        id: Int
    ) =
        getDataByIdFromRemoteSource(isRus, interactor, adult, page, id)

    fun getMoviesHistory() {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategory(
                        Category(movies = repositoryImpl.getAllHistory().toMutableList())
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun getMoviesFavorite() {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategory(
                        Category(movies = repositoryImpl.getAllFavorite().toMutableList())
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun saveMovieToFavorite(movie: Movie) {
        launch(Dispatchers.IO) {
            try {
                repositoryImpl.saveToFavorite(movie)
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    fun deleteMovieFromFavorite(movie: Movie) {
        launch(Dispatchers.IO) {
            try {
                repositoryImpl.deleteFromFavorite(movie.id)
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    private fun getDataFromRemoteSource(
        isRussian: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int
    ) {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getCategoriesFromRemoteStorage(
                            isRussian,
                            interactor,
                            adult,
                            page
                        )
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }

    private fun getDataByIdFromRemoteSource(
        isRussian: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int,
        id: Int
    ) {
        liveDataToObserve.value = AppState.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppState.SuccessCategoryById(
                        repositoryImpl.getCategoryByIdFromRemoteStorage(
                            isRussian,
                            interactor,
                            adult,
                            page,
                            id
                        )
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppState.Error(e))
            }
        }
    }
}