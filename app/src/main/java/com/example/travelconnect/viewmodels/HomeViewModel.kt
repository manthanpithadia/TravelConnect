package com.example.travelconnect.viewmodels

import ActivityApiClient
import ActivityItem
import ImageApiService
import ImageModel
import LocationItem
import WeatherResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.remote.LocationApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    val chipItems = listOf("Top Locations", "Activities", "Explore New", "History", "Food")

    private val apiService = LocationApiClient.apiService
    private val activityApiService = ActivityApiClient.activityApiService



    fun getTrendingLocations(): LiveData<List<LocationItem>> {
        val data = MutableLiveData<List<LocationItem>>()

        apiService.getTrendingLocations().enqueue(object : Callback<List<LocationItem>> {
            override fun onResponse(call: Call<List<LocationItem>>, response: Response<List<LocationItem>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    // Handle the API error
                }
            }

            override fun onFailure(call: Call<List<LocationItem>>, t: Throwable) {
                // Handle network errors
            }
        })

        return data
    }

    fun getActivities(): LiveData<List<ActivityItem>> {
        val data = MutableLiveData<List<ActivityItem>>()

        activityApiService.getActivities().enqueue(object : Callback<List<ActivityItem>> {
            override fun onResponse(call: Call<List<ActivityItem>>, response: Response<List<ActivityItem>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    // Handle the API error
                }
            }

            override fun onFailure(call: Call<List<ActivityItem>>, t: Throwable) {
                // Handle network errors
            }
        })

        return data
    }

    fun getWeatherData(
        apiKey: String,
        latitude: Double,
        longitude: Double,
        onWeatherDataReceived: (Double,String) -> Unit,
        onError: () -> Unit
    ) {
        WeatherRepository.apiService.getCurrentWeather(latitude, longitude, apiKey)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            val temperature = weatherData.main.temp
                            val locationName = weatherData.name // Get the location name
                            onWeatherDataReceived(temperature,locationName)
                        } else {
                            onError()
                        }
                    } else {
                        onError()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    onError()
                }
            })
    }

}