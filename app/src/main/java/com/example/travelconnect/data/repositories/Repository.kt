package com.example.travelconnect.data.repositories

import LocationDetails
import com.example.travelconnect.data.model.ExtendedModel
import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.model.UserProfile
import com.example.travelconnect.data.remote.ApiService
import retrofit2.Call
import retrofit2.Response

class Repository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String): Call<Any> {
        val request = LoginRequest(email = "email12", password = "abcd")
        return apiService.login(request)
    }

    fun getLocationDetails(id: String): Call<LocationDetails> {
        return apiService.getLocationDetails(id)
    }

    fun getExtendedData(name: String): Call<ExtendedModel> {
        return apiService.getExtended(name)
    }
}