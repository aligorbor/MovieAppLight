package ru.geekbrains.android2.movieapplight.model

import ru.geekbrains.android2.movieapplight.BuildConfig
import ru.geekbrains.android2.movieapplight.interactors.StringsInteractor
import ru.geekbrains.android2.movieapplight.model.database.Database
import ru.geekbrains.android2.movieapplight.model.database.FavoriteEntity
import ru.geekbrains.android2.movieapplight.model.database.HistoryEntity
import ru.geekbrains.android2.movieapplight.model.rest.*
import ru.geekbrains.android2.movieapplight.model.rest.rest_entities.CategoryDTO
import ru.geekbrains.android2.movieapplight.model.rest.rest_entities.PersonsDTO

class RepositoryImpl : Repository {

    override fun getCategoriesFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int
    ) =
        loadCategories(isRus, interactor, adult, page)

    override fun getMovieDetailFromRemoteStorage(movie: Movie) = loadMovieDetail(movie)

    override fun getPersonsPopularFromRemoteStorage(
        isRus: Boolean,
        adult: Boolean,
        page: Int
    ): Persons {
        val lang = if (isRus) languageRU
        else languageEN
        return toPersons(loadPersons(personPopular, lang, page)).apply {
            this.isRus = isRus
            this.adult = adult
        }
    }

    override fun getPersonDetailFromRemoteStorage(person: Person) = loadPersonDetail(person)

    override fun getCategoryByIdFromRemoteStorage(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int,
        id: Int
    ): Category {
        val lang = if (isRus) languageRU
        else languageEN
        var category = Category()
        when (id) {
            0 -> category = toCategory(loadCategory(categoryNowPlaying, lang, page)).apply {
                this.name = interactor.strNowPlaying
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 0
                this.isRus = isRus
                this.adult = adult
            }
            1 -> category = toCategory(loadCategory(categoryPopular, lang, page)).apply {
                this.name = interactor.strPopular
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 1
                this.isRus = isRus
                this.adult = adult
            }
            2 -> category = toCategory(loadCategory(categoryTopRated, lang, page)).apply {
                this.name = interactor.strTopRated
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 2
                this.isRus = isRus
                this.adult = adult
            }
            3 -> category = toCategory(loadCategory(categoryUpComing, lang, page)).apply {
                this.name = interactor.strUpcoming
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 3
                this.isRus = isRus
                this.adult = adult
            }
            else -> if (id >= constCategoryCount) {
                val genresDTO = BackendRepo.api.getGenres(BuildConfig.MOVIE_API_KEY, lang)
                    .execute()
                    .body()
                genresDTO?.let {
                    category = toCategory(
                        loadCategoryGenre(
                            idGenre = it.genres[id - constCategoryCount]?.id ?: 0,
                            adult = adult,
                            language = lang,
                            page = page
                        )
                    ).apply {
                        this.name = it.genres[id - constCategoryCount]?.name ?: ""
                        this.movies.map { it.isFavorite = isFavorite(it.id) }
                        this.id = id
                        this.isRus = isRus
                        this.adult = adult
                    }
                }
            }
        }
        return category
    }

    private fun loadCategories(
        isRus: Boolean,
        interactor: StringsInteractor,
        adult: Boolean,
        page: Int
    ): MutableList<Category> {
        val lang = if (isRus) languageRU
        else languageEN
        return mutableListOf(
            toCategory(loadCategory(categoryNowPlaying, lang, page)).apply {
                this.name = interactor.strNowPlaying
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 0
                this.isRus = isRus
                this.adult = adult
            },
            toCategory(loadCategory(categoryPopular, lang, page)).apply {
                this.name = interactor.strPopular
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 1
                this.isRus = isRus
                this.adult = adult
            },
            toCategory(loadCategory(categoryTopRated, lang, page)).apply {
                this.name = interactor.strTopRated
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 2
                this.isRus = isRus
                this.adult = adult
            },
            toCategory(loadCategory(categoryUpComing, lang, page)).apply {
                this.name = interactor.strUpcoming
                this.movies.map { it.isFavorite = isFavorite(it.id) }
                this.id = 3
                this.isRus = isRus
                this.adult = adult
            },
        ).apply {
            addAll(loadCategoriesOfGenres(adult, isRus, page))
        }
    }

    private fun loadCategory(categoryName: String, language: String, page: Int): CategoryDTO? =
        BackendRepo.api.getCategory(categoryName, BuildConfig.MOVIE_API_KEY, language, page)
            .execute()
            .body()

    private fun loadPersons(categoryName: String, language: String, page: Int): PersonsDTO? =
        BackendRepo.api.getPerson(categoryName, BuildConfig.MOVIE_API_KEY, language, page)
            .execute()
            .body()

    private fun loadCategoriesOfGenres(
        adult: Boolean,
        isRus: Boolean,
        page: Int
    ): MutableList<Category> {
        val language = if (isRus) languageRU
        else languageEN

        val genresDTO = BackendRepo.api.getGenres(BuildConfig.MOVIE_API_KEY, language)
            .execute()
            .body()
        val categoriesGenres = mutableListOf<Category>()
        genresDTO?.let {
            var id = constCategoryCount
            for (genreDTO in it.genres) {
                categoriesGenres.add(
                    toCategory(
                        loadCategoryGenre(
                            idGenre = genreDTO?.id ?: 0,
                            adult = adult,
                            language = language,
                            page = page
                        )
                    ).apply {
                        this.name = genreDTO?.name ?: ""
                        this.movies.map { it.isFavorite = isFavorite(it.id) }
                        this.id = id
                        this.isRus = isRus
                        this.adult = adult
                    }
                )
                id++
            }
        }
        return categoriesGenres
    }

    private fun loadCategoryGenre(
        idGenre: Int,
        adult: Boolean,
        language: String,
        page: Int
    ): CategoryDTO? =
        BackendRepo.api.getMoviesByGenre(
            BuildConfig.MOVIE_API_KEY,
            language, sortPopularityDesc, adult, idGenre, page
        )
            .execute()
            .body()


    private fun loadMovieDetail(movie: Movie): Movie {
        val lang = if (movie.isRus) languageRU
        else languageEN
        return toMovieDetail(
            movie, BackendRepo.api.getMovieDetail(movie.id, BuildConfig.MOVIE_API_KEY, lang)
                .execute()
                .body()
        )
    }

    private fun loadPersonDetail(person: Person): Person {
        val lang = if (person.isRus) languageRU
        else languageEN
        return toPersonDetail(
            person, BackendRepo.api.getPersonDetail(person.id, BuildConfig.MOVIE_API_KEY, lang)
                .execute()
                .body()
        )
    }

    override fun getAllHistory(): List<Movie> =
        convertHistoryEntitiesToMovies(Database.db.historyDao().all())

    override fun saveToHistory(movie: Movie) =
        Database.db.historyDao().insert(convertMovieToHistoryEntity(movie))

    override fun getAllFavorite(): List<Movie> =
        convertFavoriteEntitiesToMovies(Database.db.favoriteDao().all())

    override fun saveToFavorite(movie: Movie) {
        Database.db.favoriteDao().insert(convertMovieToFavoriteEntity(movie))
    }

    override fun deleteFromFavorite(id: Int) = Database.db.favoriteDao().deleteById(id)

    override fun isFavorite(id: Int): Boolean = Database.db.favoriteDao().countById(id) > 0

    override fun getNote(id: Int): String {
        val listOfEntity = Database.db.historyDao().getDataById(id)
        return if (listOfEntity.isNotEmpty())
            listOfEntity[0].note
        else
            ""
    }

    private fun convertFavoriteEntitiesToMovies(entityList: List<FavoriteEntity>): List<Movie> =
        entityList.map {
            Movie(
                id = it.id.toInt(),
                title = it.title,
                release_date = it.release_date,
                vote_average = it.vote_average,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path
            )
        }

    private fun convertHistoryEntitiesToMovies(entityList: List<HistoryEntity>): List<Movie> =
        entityList.map {
            Movie(
                id = it.id.toInt(),
                title = it.title,
                release_date = it.release_date,
                vote_average = it.vote_average,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path,
                note = it.note
            )
        }

    private fun convertMovieToHistoryEntity(movie: Movie): HistoryEntity =
        HistoryEntity(
            id = movie.id.toLong(),
            title = movie.title,
            release_date = movie.release_date,
            vote_average = movie.vote_average,
            poster_path = movie.poster_path,
            backdrop_path = movie.backdrop_path,
            note = movie.note
        )

    private fun convertMovieToFavoriteEntity(movie: Movie): FavoriteEntity =
        FavoriteEntity(
            id = movie.id.toLong(),
            title = movie.title,
            release_date = movie.release_date,
            vote_average = movie.vote_average,
            poster_path = movie.poster_path,
            backdrop_path = movie.backdrop_path
        )
}