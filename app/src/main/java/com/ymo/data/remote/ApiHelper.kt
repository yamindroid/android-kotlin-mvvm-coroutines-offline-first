package com.ymo.data.remote

import com.ymo.data.model.api.MovieResponse

interface ApiHelper {
    suspend fun loadUpComingMovies(): MovieResponse
    suspend fun loadPopularMovies(): MovieResponse
}
