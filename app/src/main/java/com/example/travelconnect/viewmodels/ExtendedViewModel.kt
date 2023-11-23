package com.example.travelconnect.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.model.ExtendedModel
import com.example.travelconnect.data.model.ExtendedViewState
import com.example.travelconnect.data.repositories.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExtendedViewModel(private val repository: Repository) : ViewModel() {
    private val _extendedDetailsLiveData = MutableLiveData<ExtendedViewState>()
    val extendedDetailsLiveData: LiveData<ExtendedViewState> get() = _extendedDetailsLiveData


    fun getExtendedDetails(location_name: String) {
        _extendedDetailsLiveData.value = ExtendedViewState.Loading

        repository.getExtendedData(location_name).enqueue(object : Callback<ExtendedModel> {
            override fun onResponse(call: Call<ExtendedModel>, response: Response<ExtendedModel>) {
                if (response.isSuccessful) {
                    val extendedDetails = response.body()
                    if (extendedDetails != null) {
                        _extendedDetailsLiveData.value = ExtendedViewState.Success(extendedDetails)
                    } else {
                        _extendedDetailsLiveData.value = ExtendedViewState.Error("Empty response body")
                    }
                } else {
                    _extendedDetailsLiveData.value = ExtendedViewState.Error("Failed to get location details")
                }
            }

            override fun onFailure(call: Call<ExtendedModel>, t: Throwable) {
                _extendedDetailsLiveData.value = ExtendedViewState.Error("API call failed")
            }
        })
    }
}