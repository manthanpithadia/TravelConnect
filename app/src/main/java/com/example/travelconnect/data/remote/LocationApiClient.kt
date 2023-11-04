package com.example.travelconnect.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocationApiClient {
    private const val BASE_URL = "https://travel-app-live-dc32b92a6df0.herokuapp.com/"
    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
