package com.example.travelconnect.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.model.SignUpRequest
import com.example.travelconnect.data.remote.ApiService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupViewModel() : ViewModel() {
    val signupResultLiveData = MutableLiveData<Boolean>()

    fun performSignup() {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create an instance of the ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Create the request body
        val request = SignUpRequest(email = "email14", password = "abcd")

        // Make the API call
        val call = apiService.signUp(request)

        call.enqueue(object : retrofit2.Callback<Any> {
            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.isSuccessful) {
                    //    Toast. makeText(context,"Sign up successful", Toast. LENGTH_SHORT)
                    Log.i("Log","Done")

                } else {
                    //   Toast. makeText(context,"Sign up failed", Toast. LENGTH_SHORT)
                    Log.i("Log","Failed")

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // Network error
                // Handle the error here
                Log.i("Log","Network error")
            }
        })
    }
}