package com.example.scenespotnersion2.remote.repository

import com.example.scenespotnersion2.remote.network.ApiService
import com.example.scenespotnersion2.remote.network.RetrofitModule

class MainRepository {
     val apiService: ApiService by lazy {
        RetrofitModule.retrofit.create(ApiService::class.java)
    }
}