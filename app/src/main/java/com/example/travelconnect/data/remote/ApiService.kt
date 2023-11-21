package com.example.travelconnect.data.remote

import ActivityItem
import ImageModel
import LocationDetails
import LocationItem
import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.model.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/auth/signup")
    fun signUp(@Body request: SignUpRequest): Call<Any> // Modify the return type as needed

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<Any> // Modify the return type as needed

    // This is to get All the Location, This can also be used as Trending locations.
    @POST("dashboard/trending")
    fun getTrendingLocations(): Call<List<LocationItem>>

    @POST("dashboard/activities")
    fun getActivities(): Call<List<ActivityItem>>

    @POST("searchScreen/allImagesShuffled")
    fun getImages(): Call<List<ImageModel>>

    @POST("location/{id}")
    fun getLocationDetails(@Path("id") id: String): Call<LocationDetails>

}