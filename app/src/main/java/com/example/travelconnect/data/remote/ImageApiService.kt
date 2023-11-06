import com.example.travelconnect.data.remote.ApiService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageApiService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://travel-app-live-dc32b92a6df0.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getImages(callback: (List<ImageModel>?) -> Unit, errorCallback: () -> Unit) {
        val call = apiService.getImages()
        call.enqueue(object : retrofit2.Callback<List<ImageModel>> {
            override fun onResponse(call: retrofit2.Call<List<ImageModel>>, response: retrofit2.Response<List<ImageModel>>) {
                if (response.isSuccessful) {
                    val imageList = response.body()
                    callback(imageList)
                } else {
                    errorCallback()
                }
            }

            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                errorCallback()
            }

        })
    }
}
