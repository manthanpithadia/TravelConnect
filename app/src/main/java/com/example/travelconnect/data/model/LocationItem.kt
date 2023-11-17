import com.google.gson.annotations.SerializedName

data class LocationItem(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("province") val province: String,
    @SerializedName("img") val img: String,
    @SerializedName("rating") val rating: Double
)
