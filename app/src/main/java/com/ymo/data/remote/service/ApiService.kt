package com.ymo.data.remote.service

import com.ymo.data.model.api.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("upcoming")
    suspend fun loadUpComingMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse


    @GET("popular")
    suspend fun loadPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

}
