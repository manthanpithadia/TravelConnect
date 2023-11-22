package com.example.travelconnect.viewmodels
// UserProfileViewModel.kt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.model.UserProfile
import com.example.travelconnect.data.remote.ApiService
import com.example.travelconnect.data.repositories.Repository
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.saveToSharedPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileViewModel(private val repository: Repository) : ViewModel() {

    private val _userProfileLiveData = MutableLiveData<UserProfile>()
    val userProfileLiveData: LiveData<UserProfile> get() = _userProfileLiveData

    /*
        fun getUserProfile(token: String) {
            _userProfileLiveData.value = UserProfileViewState.Loading

            repository.getUserProfile(token).enqueue(object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if (response.isSuccessful) {
                        val userProfile = response.body()
                        if (userProfile != null) {
                            _userProfileLiveData.value = UserProfileViewState.Success(userProfile)
                        } else {
                            _userProfileLiveData.value = UserProfileViewState.Error("Empty response body")
                        }
                    } else {
                        _userProfileLiveData.value = UserProfileViewState.Error("Failed to get user profile")
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    _userProfileLiveData.value = UserProfileViewState.Error("API call failed")
                }
            })
        }
    */

    fun getUserProfile(token: String) {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create an instance of the ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Create the request body

        // Make the API call
        val call = apiService.getUserProfile(token)

        call.enqueue(object : retrofit2.Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: retrofit2.Response<UserProfile>) {
                if (response.code() == 200) {
                    //    Toast. makeText(context,"Sign up successful", Toast. LENGTH_SHORT)
                    val responseBody = response.body()
                    _userProfileLiveData.value = responseBody!!
                    if (responseBody != null) {
                        Log.d("Log","$token")
                        Log.d("Log","${responseBody.user.email}")
                    }

                } else {

                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                // Network error
                // Handle the error here
                Log.i("Log","Network error")
            }
        })
    }

}
