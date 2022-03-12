package com.ymo.data.local.db

import com.ymo.data.model.api.MovieItem


interface DBHelper {
    suspend fun getPopularDBMovies(): List<MovieItem>
    suspend fun getUpcomingDBMovies(): List<MovieItem>
    suspend fun deleteMovieItemById(id: Int): Int
    suspend fun addMovieItem(movieItem: MovieItem)
    suspend fun insertAllMovieItems(movieItems: List<MovieItem?>?)
    suspend fun updateMovie(movieItem: MovieItem)
    suspend fun deleteAllMovieItems()
}
