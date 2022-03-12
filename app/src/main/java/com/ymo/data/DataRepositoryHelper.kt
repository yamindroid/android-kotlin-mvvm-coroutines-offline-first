package com.ymo.data

import com.ymo.data.local.db.DBHelper
import com.ymo.data.model.api.MovieItem

interface DataRepositoryHelper :DBHelper {
    suspend fun getUpComingMovies(): List<MovieItem?>?
    suspend fun getPopularMovies(): List<MovieItem?>?
}
