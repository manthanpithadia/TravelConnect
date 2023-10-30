package com.example.travelconnect.data.remote

import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.model.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/signup")
    fun signUp(@Body request: SignUpRequest): Call<Any> // Modify the return type as needed

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<Any> // Modify the return type as needed
}