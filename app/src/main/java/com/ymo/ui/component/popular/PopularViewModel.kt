package com.ymo.ui.component.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ymo.data.DataRepositoryHelper
import com.ymo.data.Resource
import com.ymo.data.model.api.MovieItem
import com.ymo.data.model.api.MovieResponse
import com.ymo.data.model.error.NETWORK_ERROR
import com.ymo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val dataRepositoryHelper: DataRepositoryHelper
) : BaseViewModel() {
    private val movies = MutableLiveData<Resource<List<MovieItem?>?>>()
    val movieLiveData: LiveData<Resource<List<MovieItem?>?>> get() = movies

    private val addFavoriteStatus = MutableLiveData<Resource<Unit>>()
    val addFavoriteStatusLiveData: LiveData<Resource<Unit>> get() = addFavoriteStatus

    fun loadMovies() {
        movies.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                movies.postValue(Resource.success(dataRepositoryHelper.getPopularMovies()))
            } catch (e: HttpException) {
                movies.postValue(Resource.error(errorManager.getHttpError(e).description, null))
            } catch (e: Exception) {
                movies.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
            }

        }
        if (!network.isConnected) {
            movies.postValue(
                Resource.error(
                    errorManager.getError(NETWORK_ERROR).description,
                    null
                )
            )
        }
    }


    fun addFavoriteMovie(movieItem: MovieItem) {
        movieItem.isFavourite = true
        viewModelScope.launch {
            addFavoriteStatus.postValue(Resource.loading(null))
            try {
                addFavoriteStatus.value =  Resource.success(
                    dataRepositoryHelper.addMovieItem(
                      movieItem
                    )
                )
            } catch (e: Exception) {
                addFavoriteStatus.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
            }

        }
    }

}