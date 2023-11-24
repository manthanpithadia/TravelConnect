package com.example.travelconnect.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travelconnect.data.model.SignUpRequest
import com.example.travelconnect.data.remote.ApiService
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.saveToSharedPreferences
import com.google.androidgamesdk.gametextinput.Listener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupViewModel(val application: Application) : ViewModel() {
    val signupResultLiveData = MutableLiveData<Boolean>()

    fun performSignup(email:String, pass: String) {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create an instance of the ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Create the request body
        val request = SignUpRequest(email,pass)

        // Make the API call
        val call = apiService.signUp(request)

        call.enqueue(object : retrofit2.Callback<Any> {
            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.code() == 201) {
                    val responseBody = response.body()
                    Log.d("Log",responseBody.toString())
                    if (responseBody is Map<*, *>) {
                        val token = responseBody["token"] as? String
                        //val avatarString = responseBody["avatarString"] as? String

                        // Store the token and other information in SharedPreferences
                        application.saveToSharedPreferences(token,"")
                        application.getFromSharedPreferences()
                        // Log success
                        Log.i("Log", "Sign up successful")
                        signupResultLiveData.value = true
                    } else {
                        // Log error
                        Log.i("Log", "Invalid response body")
                    }
                    Log.i("Log","Done")

                } else {
                    //   Toast. makeText(context,"Sign up failed", Toast. LENGTH_SHORT)
                    Log.i("Log","Failed")
                    signupResultLiveData.value = false
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // Network error
                // Handle the error here
                Log.i("Log","Network error")
            }
        })
    }

    /*fun signUp(email: String, password: String) {
        val url = "https://travel-app-live-dc32b92a6df0.herokuapp.com/auth/signup"

        // Create JSON object with input parameters
        val params = JSONObject()
        params.put("email", email)
        params.put("password", password)

        // Create a request using the JsonObjectRequest
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                // Handle the success response
                val token = response.optString("token")
                val msg = response.optString("message")
                Log.d("Log", "User registered successfully. Token: $token , $msg")
                // You can do further processing or update LiveData here
            },
            { error ->
                // Handle the error response
                Log.e("YourViewModel", "Error registering user: ${error.message}")
                // You can do further error handling or update LiveData here
            }
        )

        // Add the request to the request queue
        Volley.newRequestQueue(application).add(jsonObjectRequest)
    }*/

    fun saveDataToPreferences(context: Context, token: String?, avatarString: String?) {
        context.saveToSharedPreferences(token, avatarString)
    }
}