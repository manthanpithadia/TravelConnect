package com.example.travelconnect.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.model.SignUpRequest
import com.example.travelconnect.data.remote.ApiService
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.saveToSharedPreferences
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupViewModel(val application: Application) : ViewModel() {
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
        val request = SignUpRequest(email = "email14123", password = "abcd564654")

        // Make the API call
        val call = apiService.signUp(request)

        call.enqueue(object : retrofit2.Callback<Any> {
            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody is Map<*, *>) {
                        val token = responseBody["token"] as? String
                        val avatarString = responseBody["avatarString"] as? String

                        // Store the token and other information in SharedPreferences
                        application.saveToSharedPreferences(token,avatarString)
                        application.getFromSharedPreferences()
                        // Log success
                        Log.i("Log", "Sign up successful")
                    } else {
                        // Log error
                        Log.i("Log", "Invalid response body")
                    }
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
    fun saveDataToPreferences(context: Context, token: String?, avatarString: String?) {
        context.saveToSharedPreferences(token, avatarString)
    }
}