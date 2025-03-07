package com.example.scenespotnersion2.main.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.main.details.data.Results
import com.example.scenespotnersion2.remote.data.MovieResponse
import com.example.scenespotnersion2.remote.data.Result
import com.example.scenespotnersion2.remote.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.Response

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private val _seriesDetails = MutableLiveData<Results?>()
    val seriesDetails : LiveData<Results?> = _seriesDetails

    fun retrieveGetSeriesByImdb(seriesId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // تعيين حد زمني للطلب (8 ثوانٍ كحد أقصى)
                withTimeout(60000) {
                    val responseDeferred = async { repository.moviesApiService.getSeriesByIMDb(seriesId) }
                    val response = responseDeferred.await()

                    if (response.isSuccessful) {
                        _seriesDetails.postValue(response.body()?.results)
                        Log.e("DetailsViewModel", "Success: ${response.body()}")
                    } else {
                        Log.e("DetailsViewModel", "Error: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Timeout/Error: ${e.message}")
            }
        }
    }
}