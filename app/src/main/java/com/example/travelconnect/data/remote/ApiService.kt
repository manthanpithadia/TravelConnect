package com.example.travelconnect.data.remote

import ActivityItem
import ImageModel
import LocationDetails
import LocationItem
import com.example.travelconnect.data.model.AddTripRequest
import com.example.travelconnect.data.model.ExtendedModel
import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.model.RestaurentItem
import com.example.travelconnect.data.model.SignUpRequest
import com.example.travelconnect.data.model.UserProfile
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("dashboard/restaurants")
    fun getRestaurent(): Call<List<RestaurentItem>>

    @POST("searchScreen/allImagesShuffled")
    fun getImages(): Call<List<ImageModel>>

    @POST("location/{id}")
    fun getLocationDetails(@Path("id") id: String): Call<LocationDetails>

    @POST("dashboard/{name}")
    fun getExtended(@Path("name") name: String): Call<ExtendedModel>

    @POST("trip/addtrip")
    fun addTrip(@Body request: AddTripRequest): Call<Any>

   /* @POST("auth/profile")
    fun getUserProfile(@Header("token") token: String): Call<UserProfile>*/
   @POST("auth/profile")
   fun getUserProfile(@Header("token") token: String): Call<UserProfile>

}