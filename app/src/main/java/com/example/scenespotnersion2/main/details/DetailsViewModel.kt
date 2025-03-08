package com.example.scenespotnersion2.main.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.main.details.data.Results
import com.example.scenespotnersion2.main.details.data.episodedata.EpisodeDBItem
import com.example.scenespotnersion2.remote.data.CastDBItem
import com.example.scenespotnersion2.remote.data.SeasonDBItem
import com.example.scenespotnersion2.remote.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _seriesDetails = MutableLiveData<Results?>()
    val seriesDetails : LiveData<Results?> = _seriesDetails


    private var _seasons = MutableLiveData<List<SeasonDBItem>>()
    val seasons: LiveData<List<SeasonDBItem>> = _seasons

    private var _episodes = MutableLiveData<List<EpisodeDBItem>>()
    val episodes: LiveData<List<EpisodeDBItem>> = _episodes

    private var _cast = MutableLiveData<List<CastDBItem>>()
    val cast: LiveData<List<CastDBItem>> = _cast

    fun retrieveGetSeriesByImdb(seriesId: String) {
        viewModelScope.launch {
            try {
                withTimeout(120000) {
                    val responseDeferred = async { repository.moviesApiService.getSeriesByIMDb(seriesId) }
                    val response = responseDeferred.await()
                    if (response.isSuccessful) {
                        _seriesDetails.postValue(response.body()?.results)
                        Log.e(TAG, "Success: ${response.body()}")
                    } else {
                        Log.e(TAG, "Error: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Timeout/Error: ${e.message}")
            }
        }
    }

    fun retrieveGetShowSeasonsById(showId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.seriesApiService.fetchShowSeasonsById(showId)
                if (response.isSuccessful) {
                    _seasons.postValue(response.body()?.filterNotNull())
                } else {
                    Log.e(TAG, "retrieveGetShowSeasonsById: ")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "retrieveGetShowSeasonsById: ${e.message}")
        }
    }

    fun fetchShowSeasonEpisodesListById(showId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.seriesApiService.fetchShowSeasonEpisodesListById(showId)
                Log.e(TAG, "fetchShowSeasonEpisodesListById: ${response.body()?.filterNotNull()}")
                if (response.isSuccessful) {
                    _episodes.postValue(response.body()?.filterNotNull())
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchShowSeasonEpisodesListById: ${e.message}")
        }

    }

    fun getCastByShowId(showId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.seriesApiService.fetchCastByShowId(showId)
                if (response.isSuccessful) {
                    _cast.postValue(response.body())
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCastByShowId: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel"
    }

}