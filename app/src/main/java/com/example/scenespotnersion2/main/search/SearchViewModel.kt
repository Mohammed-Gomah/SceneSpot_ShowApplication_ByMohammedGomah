package com.example.scenespotnersion2.main.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.scenespotnersion2.remote.data.SeasonDBItem
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import com.example.scenespotnersion2.remote.repository.MainRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _searchList = MutableLiveData<List<SeriesDBItem>>()
    val seriesList : LiveData<List<SeriesDBItem>> = _searchList

    fun searchSeriesByName(showName:String){
        try {
            viewModelScope.launch {
                val response = repository.seriesApiService.searchSeriesByName(showName)
                if (response.isSuccessful){
                    _searchList.postValue(response.body()?.map { it.show })
                }else{
                    Log.e(TAG, "searchSeriesByName: ${response.body()} " )
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    companion object{
        private const val TAG = "SearchViewModel"
    }
}