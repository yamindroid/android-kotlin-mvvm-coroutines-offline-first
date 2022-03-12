package com.ymo.data.local.db.Movie

import androidx.room.*
import com.ymo.data.model.api.MovieItem

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE isPopular = 1")
    suspend fun getPopularDBMovies(): List<MovieItem>

    @Query("SELECT * FROM movie WHERE isUpcoming = 1")
    suspend fun getUpcomingDBMovies(): List<MovieItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieItem(movieItem: MovieItem)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovieItemById(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovieItems(movieItems: List<MovieItem?>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movieItem: MovieItem)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovieItems()


}