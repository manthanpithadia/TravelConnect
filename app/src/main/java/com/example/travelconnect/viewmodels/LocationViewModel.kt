import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.repositories.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel(private val repository: Repository) : ViewModel() {

    private val _locationDetailsLiveData = MutableLiveData<LocationViewState>()
    val locationDetailsLiveData: LiveData<LocationViewState> get() = _locationDetailsLiveData


    fun getLocationDetails(id: String) {
        _locationDetailsLiveData.value = LocationViewState.Loading

        repository.getLocationDetails(id).enqueue(object : Callback<LocationDetails> {
            override fun onResponse(call: Call<LocationDetails>, response: Response<LocationDetails>) {
                if (response.isSuccessful) {
                    val locationDetails = response.body()
                    if (locationDetails != null) {
                        _locationDetailsLiveData.value = LocationViewState.Success(locationDetails)
                    } else {
                        _locationDetailsLiveData.value = LocationViewState.Error("Empty response body")
                    }
                } else {
                    _locationDetailsLiveData.value = LocationViewState.Error("Failed to get location details")
                }
            }

            override fun onFailure(call: Call<LocationDetails>, t: Throwable) {
                _locationDetailsLiveData.value = LocationViewState.Error("API call failed")
            }
        })
    }

}
