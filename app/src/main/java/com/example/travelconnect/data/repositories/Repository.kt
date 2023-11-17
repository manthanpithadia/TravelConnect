package com.example.travelconnect.data.repositories

import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.remote.ApiService
import retrofit2.Call
import retrofit2.Response

class Repository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String): Call<Any> {
        val request = LoginRequest(email = "email12", password = "abcd")
        return apiService.login(request)
    }
}