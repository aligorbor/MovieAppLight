package ru.geekbrains.android2.movieapplight.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.geekbrains.android2.movieapplight.model.Movie
import ru.geekbrains.android2.movieapplight.model.RepositoryImpl

class DetailsViewModel(
    private val liveDataToObserve: MutableLiveData<AppStateDetails> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getMovieDetailFromRemoteSource(movie: Movie) = getDataFromRemoteSource(movie)

    private fun getDataFromRemoteSource(movie: Movie) {
        liveDataToObserve.value = AppStateDetails.Loading
        launch(Dispatchers.IO) {
            try {
                liveDataToObserve.postValue(
                    AppStateDetails.Success(
                        repositoryImpl.getMovieDetailFromRemoteStorage(movie).apply {
                            note = repositoryImpl.getNote(movie.id)
                        }
                    )
                )
            } catch (e: Exception) {
                liveDataToObserve.postValue(AppStateDetails.Error(e))
            }
        }
    }

    fun saveMovieToHistory(movie: Movie) {
        launch(Dispatchers.IO) {
            repositoryImpl.saveToHistory(movie)
        }
    }
}