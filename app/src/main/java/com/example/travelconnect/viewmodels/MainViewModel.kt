import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelconnect.data.model.LoginRequest
import com.example.travelconnect.data.remote.ApiService
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.saveToSharedPreferences
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(val application: Application) : ViewModel() {

    val loginResultLiveData = MutableLiveData<Boolean>()

    fun performLogin(email:String, pass: String) {
        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/") // Your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create an instance of the ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Create the request body
        val request = LoginRequest(email,pass)

        // Make the API call
        val call = apiService.login(request)

        call.enqueue(object : retrofit2.Callback<Any> {
            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.code() == 200) {
                //    Toast. makeText(context,"Sign up successful", Toast. LENGTH_SHORT)
                    val responseBody = response.body()
                    if (responseBody is Map<*, *>) {
                        val token = responseBody["token"] as? String
                        val avatarString = responseBody["avatarString"] as? String

                        // Store the token and other information in SharedPreferences
                        application.saveToSharedPreferences(token,avatarString)
                        application.getFromSharedPreferences()
                        // Log success
                        Log.i("Log", "Sign up successful")
                        loginResultLiveData.value = true
                    } else {
                        // Log error
                        Log.i("Log", "Invalid response body")
                    }
                    Log.i("Log","Done")

                } else {
                 //   Toast. makeText(context,"Sign up failed", Toast. LENGTH_SHORT)
                    Log.i("Log","Failed")
                    loginResultLiveData.value = false
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
