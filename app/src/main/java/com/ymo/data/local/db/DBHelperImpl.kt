package com.ymo.data.local.db

import com.ymo.data.model.api.MovieItem
import javax.inject.Inject

class DBHelperImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : DBHelper {
    override suspend fun getPopularDBMovies() = appDatabase.movieDao().getPopularDBMovies()

    override suspend fun getUpcomingDBMovies(): List<MovieItem> = appDatabase.movieDao().getUpcomingDBMovies()

    override suspend fun addMovieItem(movieItem: MovieItem) =
        appDatabase.movieDao().addMovieItem(movieItem)

    override suspend fun deleteMovieItemById(id: Int): Int =
        appDatabase.movieDao().deleteMovieItemById(id)

    override suspend fun updateMovie(movieItem: MovieItem) = appDatabase.movieDao().updateMovie(movieItem)
    override suspend fun deleteAllMovieItems() = appDatabase.movieDao().deleteAllMovieItems()

    override suspend fun insertAllMovieItems(movieItems: List<MovieItem?>?)=appDatabase.movieDao().insertAllMovieItems(movieItems)
}
