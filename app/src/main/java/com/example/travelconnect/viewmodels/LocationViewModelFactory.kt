import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.data.repositories.Repository

@Suppress("UNREACHABLE_CODE")
class LocationViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        return super.create(modelClass)
    }
}
