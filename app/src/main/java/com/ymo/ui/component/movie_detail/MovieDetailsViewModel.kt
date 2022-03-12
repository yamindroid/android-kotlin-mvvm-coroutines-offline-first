package com.ymo.ui.component.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ymo.data.DataRepositoryHelper
import com.ymo.data.Resource
import com.ymo.data.model.api.MovieItem
import com.ymo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val dataRepositoryHelper: DataRepositoryHelper
) : BaseViewModel() {

    private val addFavoriteStatus = MutableLiveData<Resource<Unit>>()
    val addFavoriteStatusLiveData: LiveData<Resource<Unit>> get() = addFavoriteStatus

    private val removeFavoriteStatus = MutableLiveData<Resource<Int>>()
    val removeFavoriteStatusLiveData: LiveData<Resource<Int>> get() = removeFavoriteStatus

    fun addFavoriteMovie(movieItem: MovieItem) {
        movieItem.isFavourite = true
        viewModelScope.launch {
            addFavoriteStatus.postValue(Resource.loading(null))
            try {
                addFavoriteStatus.value = Resource.success(
                    dataRepositoryHelper.addMovieItem(
                        movieItem
                    )
                )
            } catch (e: Exception) {
                addFavoriteStatus.postValue(Resource.error(e.localizedMessage ?: e.message!!, null))
            }

        }
    }

    fun removeFavoriteMovie(movieItem: MovieItem) {
        viewModelScope.launch {
            removeFavoriteStatus.postValue(Resource.loading(null))
            try {
                removeFavoriteStatus.value = Resource.success(
                    dataRepositoryHelper.deleteMovieItemById(movieItem.id)
                )
            } catch (e: Exception) {
                removeFavoriteStatus.postValue(
                    Resource.error(
                        e.localizedMessage ?: e.message!!,
                        null
                    )
                )
            }

        }
    }
}

