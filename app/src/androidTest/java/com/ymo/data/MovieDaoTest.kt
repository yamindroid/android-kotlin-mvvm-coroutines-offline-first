package com.android.artgallery.data

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.ymo.data.local.db.AppDatabase
import com.ymo.data.model.api.MovieItem
import com.ymo.utils.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var mDatabase: AppDatabase

    @Before
    fun createDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getTargetContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun isMovieListEmpty() = runBlocking {
        assertEquals(0, mDatabase.movieDao().getPopularDBMovies().size)
    }

    @Test
    @Throws(Exception::class)
    fun addMovieItem() = runBlocking {
        val movieItem: MovieItem = TestUtil.createMovie(7)
        val insertedPhoto = mDatabase.movieDao().addMovieItem(movieItem)
        assertNotNull(insertedPhoto)
    }

    @Test
    @Throws(Exception::class)
    fun getPopularDBMovies() = runBlocking {
        val movieList = TestUtil.makePopularMovieList(5)
        movieList.forEach {
            mDatabase.movieDao().addMovieItem(it)
        }
        val loadedMovies = mDatabase.movieDao().getPopularDBMovies()
        assertThat(movieList, equalTo(loadedMovies))
    }

    @Test
    @Throws(Exception::class)
    fun getUpcomingDBMovies() = runBlocking {
        val movieList = TestUtil.makeUpcomingMovieList(5)
        movieList.forEach {
            mDatabase.movieDao().addMovieItem(it)
        }
        val loadedMovies = mDatabase.movieDao().getUpcomingDBMovies()
        assertThat(movieList, equalTo(loadedMovies))
    }

}
