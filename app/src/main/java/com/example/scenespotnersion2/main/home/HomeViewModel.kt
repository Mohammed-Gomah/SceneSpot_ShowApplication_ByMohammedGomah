package com.example.scenespotnersion2.main.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.remote.data.MovieResponse
import com.example.scenespotnersion2.remote.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _movies = MutableLiveData<List<MovieResponse?>>()
    val movies: LiveData<List<MovieResponse?>> = _movies


    fun getMoviesOrderedByPopularity() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.apiService.getMoviesOrderedByPopularity()
                if (response.isSuccessful) {
                    val moviesList = response.body()?.movieResponse ?: emptyList()
                    Log.e("HomeViewModel", "Movies List: $moviesList")

                    val updatedMovies = moviesList.map { movie ->
                        async {
                            val imageUrl = movie.imdbId?.let { getMovieById(it) }
                            movie.copy(imageUrl = imageUrl)
                        }
                    }.awaitAll()

                    Log.e("HomeViewModel", "Updated Movies: $updatedMovies")
                    _movies.postValue(updatedMovies)
                } else {
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                    _movies.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception: ${e.message}")
            }
        }
    }

    private suspend fun getMovieById(movieId: String): String? {
        return try {
            val response = repository.apiService.getMovieById(movieId)
            if (response.isSuccessful) {
                val movie = response.body()?.movieResponse?.firstOrNull()
                Log.e("getMovieById", "Movie Found: $movie")
                movie?.imdbId
            } else {
                Log.e("getMovieById", "Failed Response: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("getMovieById", "Exception: ${e.message}")
            null
        }
    }
}