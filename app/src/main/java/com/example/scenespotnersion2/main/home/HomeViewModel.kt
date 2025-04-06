package com.example.scenespotnersion2.main.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.remote.data.MovieResponse
import com.example.scenespotnersion2.remote.data.Result
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import com.example.scenespotnersion2.remote.repository.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _movies = MutableLiveData<List<Result?>>()
    val movies: LiveData<List<Result?>> get() = _movies

    private var _series = MutableLiveData<List<SeriesDBItem>>()
    val series: LiveData<List<SeriesDBItem>> get() = _series

private var _seriesDescription = MutableLiveData<List<SeriesDBItem>>()
    val seriesDescription: LiveData<List<SeriesDBItem>> get() = _seriesDescription

    private val sharedPref: SharedPreferences =
        application.getSharedPreferences("moviesPref", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()
    private val gson = Gson()

    private fun saveMoviesToSharedPref(movies: List<Result?>) {
        val moviesToJson = gson.toJson(movies)
        editor.putString("saved_movies", moviesToJson).apply()
    }

    private fun loadMoviesFromSharedPref(): List<Result>? {
        val json = sharedPref.getString("saved_movies", null)
        return if (json.isNullOrEmpty()) {
            null
        } else {
            val type = object : TypeToken<List<Result>>() {}.type
            gson.fromJson(json, type)
        }
    }

    private fun saveSeriesToSharedPref(series: List<SeriesDBItem>) {
        val seriesToJson = gson.toJson(series)
        editor.putString("saved_series", seriesToJson).apply()
    }

    private fun loadSeriesFromSharedPref(): List<SeriesDBItem> {
        val seriesList = sharedPref.getString("saved_series", null)
        return if (seriesList.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(seriesList, object : TypeToken<List<SeriesDBItem>>() {}.type)
        }
    }

    init {

        val movies = loadMoviesFromSharedPref()
        val series = loadSeriesFromSharedPref()
        if (!movies.isNullOrEmpty()) {
            movies.let { _movies.value = it.shuffled().shuffled() } // بدلاً من `postValue`
        }
        if (series.isNotEmpty()) {
            _series.value = series.shuffled()
        }
//        loadMoviesFromSharedPref()?.let {
//            if (it.isNotEmpty()) _movies.value = it.shuffled()
//            else fetchAllMoviesIMDB()
//        }
    }

    fun fetchAllMoviesIMDB() {
        if (_movies.value.isNullOrEmpty()) {

            try {
                viewModelScope.launch() {
                    val response = repository.moviesApiService.getMoviesOrderedByPopularity()
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
                        _movies.postValue(updateMovieList.shuffled().take(4))
                        saveMoviesToSharedPref(updateMovieList.shuffled())
                    } else {
                        Log.e(TAG, "fetchAllMoviesIMDB: $response")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private suspend fun getMoviesByIMDB(imdb: String): MovieResponse? {
        return try {
            val response = repository.moviesApiService.getMovieById(imdb)
            if (response.isSuccessful) {
                response.body()?.movieResponse
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchAllSeries() {
        try {
            if (_series.value.isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = repository.seriesApiService.fetchAllShows()
                    if (response.isSuccessful) {
                        val seriesList = response.body()?.filterNotNull() ?: emptyList()
                        _series.postValue(seriesList)
                        saveSeriesToSharedPref(seriesList.shuffled())
                    } else {
                        Log.e(TAG, "fetchAllSeries: ${response.body()}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchAllSeries: ${e.message}")
        }


    }  fun fetchAllSeriesWithDescription() {
        try {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = repository.seriesApiService.fetchAllShows()
                    if (response.isSuccessful) {
                        val seriesList = response.body()?.filterNotNull() ?: emptyList()
                        _seriesDescription.postValue(seriesList.shuffled().take(10))
                    } else {
                        Log.e(TAG, "fetchAllSeries: ${response.body()}")
                    }
                }

        } catch (e: Exception) {
            Log.e(TAG, "fetchAllSeries: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}