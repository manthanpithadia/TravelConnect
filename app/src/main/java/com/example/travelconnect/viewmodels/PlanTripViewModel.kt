package com.example.travelconnect.viewmodels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.model.AddTripRequest
import com.example.travelconnect.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanTripViewModel : ViewModel() {
    val addTripResultLiveData = MutableLiveData<Boolean>()

    fun addTrip(token: String, location: String, startDate: String, endDate: String, foodPref: String) {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the ApiService
        val apiService = retrofit.create(ApiService::class.java)

        val tripRequest = AddTripRequest(token, location, startDate, endDate, foodPref)

        // Make the API call
        val call = apiService.addTrip(tripRequest)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    // Handle success
                    Log.i("Log", "Trip added successfully")
                    addTripResultLiveData.value = true
                } else {
                    // Handle error
                    Log.e("Log", "Failed to add trip")
                    addTripResultLiveData.value = false
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // Network error
                // Handle the error here
                Log.e("Log", "Network error")
                addTripResultLiveData.value = false
            }
        })
    }
}
