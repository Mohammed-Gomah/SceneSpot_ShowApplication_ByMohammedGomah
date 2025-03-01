package com.example.scenespotnersion2.main.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.remote.data.MovieResponse
import com.example.scenespotnersion2.remote.data.Result
import com.example.scenespotnersion2.remote.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _movies = MutableLiveData<List<Result?>>()
    val movies: LiveData<List<Result?>> = _movies


    fun fetchAllMoviesIMDB() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.apiService.getMoviesOrderedByPopularity()
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results ?: emptyList()

                    val updateMovieList = moviesList.map { movie ->
                        async {
                            val deferredMoviePoster =
                                async { movie?.imdbId?.let { getMoviesByIMDB(it) } }.await()
                            val deferredMovieIMDB =
                                async { movie?.imdbId?.let { getMoviesByIMDB(it) } }.await()
                            movie?.copy(
                                rating = deferredMovieIMDB?.rating,
                                banner = deferredMoviePoster?.banner
                            )
                        }
                    }.awaitAll()
                    _movies.postValue(updateMovieList.take(4))
                } else {
                    Log.e(TAG, "fetchAllMoviesIMDB: $response")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private suspend fun getMoviesByIMDB(imdb: String): MovieResponse? {
        return try {
            val response = repository.apiService.getMovieById(imdb)
            if (response.isSuccessful) {
                response.body()?.movieResponse
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}