// com.yourpackage.data.local.entities.LocationEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val province: String,
    val imageUrl: String,
    val rating: Double
)
