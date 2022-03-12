package com.ymo.utils

import com.ymo.data.model.api.MovieItem


object TestUtil {

    fun createMovie(id: Int) = MovieItem(
        id = id,
        title = "",
    )

    fun makeUpcomingMovieList(size: Int): MutableList<MovieItem> {
        val list = ArrayList<MovieItem>(size)
        list.forEach {
            var movieItem = MovieItem(id =(list.indexOf(it) + 1) ,title ="MovieItem ${list.indexOf(it)}",isUpcoming = true)
            list.add(movieItem)
        }
        return list
    }

    fun makePopularMovieList(size: Int): MutableList<MovieItem> {
        val list = ArrayList<MovieItem>(size)
        list.forEach {
            var movieItem = MovieItem(id =(list.indexOf(it) + 1) ,title ="MovieItem ${list.indexOf(it)}",isPopular = true)
            list.add(movieItem)
        }
        return list
    }

}
