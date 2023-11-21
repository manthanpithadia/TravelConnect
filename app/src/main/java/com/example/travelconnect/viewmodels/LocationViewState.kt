sealed class LocationViewState {
    object Loading : LocationViewState()
    data class Success(val locationDetails: LocationDetails) : LocationViewState()
    data class Error(val errorMessage: String) : LocationViewState()
}
