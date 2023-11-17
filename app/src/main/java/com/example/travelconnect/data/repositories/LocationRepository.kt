// com.yourpackage.data.repositories.LocationRepository

import android.content.Context

class LocationRepository(context: Context) {

    private val locationDao: LocationDao = AppDatabase.getDatabase(context).locationDao()

    suspend fun insertLocations(locations: List<LocationEntity>) {
        locationDao.insertLocations(locations)
    }

    suspend fun saveLocationsToDb(locations: List<LocationEntity>) {
        locationDao.insertLocations(locations)
    }

    suspend fun getTrendingLocationsFromDb(): List<LocationEntity> {
        return locationDao.getLocations()
    }
}
