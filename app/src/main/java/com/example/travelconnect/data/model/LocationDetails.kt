data class LocationDetails(
    val name: String,
    val img: List<String>,
    val restaurants: List<Restaurant>,
    val activities: List<Activity>
)

data class Restaurant(
    val name: String,
    val img: List<String>
)

data class Activity(
    val name: String,
    val img: List<String>
)
