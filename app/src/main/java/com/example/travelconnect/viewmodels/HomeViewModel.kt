package com.example.travelconnect.viewmodels

import ActivityApiClient
import ActivityItem
import DBHelper
import LocationItem
import WeatherRepository
import WeatherResponse
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelconnect.data.model.RestaurentItem
import com.example.travelconnect.data.remote.LocationApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val application: Context): ViewModel()  {

    val chipItems = listOf("Top Locations", "Activities", "Explore New", "History", "Food")

    private val apiService = LocationApiClient.apiService
    private val activityApiService = ActivityApiClient.activityApiService
    //private val repository: LocationRepository = LocationRepository(application)
    private lateinit var dbHelper: DBHelper

    //TODO: saveLocation into Local DB
    fun getTrendingLocations(): LiveData<List<LocationItem>> {
        val data = MutableLiveData<List<LocationItem>>()

        apiService.getTrendingLocations().enqueue(object : Callback<List<LocationItem>> {
            override fun onResponse(call: Call<List<LocationItem>>, response: Response<List<LocationItem>>) {
                if (response.isSuccessful) {
                    val locations = response.body()

                    // Save locations to the local database
                    locations?.let {
                        viewModelScope.launch {
                            //repository.saveLocationsToDb(it.toLocationEntities())
                            //TODO: add insert and retrieve function here.
                            dbHelper = DBHelper(application)

                            //dbHelper.clearAllLocations()
                            // Example: Insert data
                            //val locationItem = LocationItem("1", "Location One", "Province A", "image_url", 4.5)
                            locations.forEach { item->
                                dbHelper.insertLocation(item)
                            }

                            // Example: Query data
                            /*val loc = dbHelper.queryLocations()

                            for (item in loc) {
                                Log.d("location",item.toString())
                            }*/
                            data.value = it
                        }
                    }

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

    fun getRestaurents(): LiveData<List<RestaurentItem>> {
        val data = MutableLiveData<List<RestaurentItem>>()

        activityApiService.getRestaurent().enqueue(object : Callback<List<RestaurentItem>> {
            override fun onResponse(call: Call<List<RestaurentItem>>, response: Response<List<RestaurentItem>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    // Handle the API error
                }
            }

            override fun onFailure(call: Call<List<RestaurentItem>>, t: Throwable) {
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