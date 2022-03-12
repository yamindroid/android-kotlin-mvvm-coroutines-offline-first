package com.ymo.data

import com.ymo.data.local.db.DBHelper
import com.ymo.data.model.api.MovieItem
import com.ymo.data.remote.ApiHelper
import com.ymo.di.NetworkModule
import kotlinx.coroutines.*
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
      private val dbHelper: DBHelper,
    private val network: NetworkModule.Network
) : DataRepositoryHelper {

    override suspend fun getUpComingMovies(): List<MovieItem?>? {
        return withContext(Dispatchers.IO) {
            if (network.isConnected) {
                val response = apiHelper.loadUpComingMovies()
                val apiPopularMovies = response.results
                apiPopularMovies?.map { movieItem ->
                    movieItem?.isUpcoming = true
                    movieItem?.let {
                        dbHelper.addMovieItem(it)
                    }
                }
                return@withContext apiPopularMovies
            } else {
                return@withContext dbHelper.getUpcomingDBMovies()
            }
        }
    }


    override suspend fun getPopularMovies(): List<MovieItem?>? {
        return withContext(Dispatchers.IO) {
            if (network.isConnected) {
                val response = apiHelper.loadPopularMovies()
                val apiPopularMovies = response.results
                apiPopularMovies?.map { movieItem ->
                    movieItem?.isPopular = true
                    movieItem?.let {
                        dbHelper.addMovieItem(it)
                    }
                }
                return@withContext apiPopularMovies
            } else {
                return@withContext dbHelper.getPopularDBMovies()
            }
        }
    }

    override suspend fun getPopularDBMovies(): List<MovieItem> = dbHelper.getPopularDBMovies()

    override suspend fun getUpcomingDBMovies(): List<MovieItem> = dbHelper.getUpcomingDBMovies()

    override suspend fun addMovieItem(movieItem: MovieItem) = dbHelper.addMovieItem(movieItem)

    override suspend fun deleteMovieItemById(id: Int) = dbHelper.deleteMovieItemById(id)

}
