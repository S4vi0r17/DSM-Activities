package tech.s4vi0r.flightsearch

import android.app.Application
import tech.s4vi0r.flightsearch.data.FlightSearchRepository
import tech.s4vi0r.flightsearch.data.local.FlightSearchDatabase
import tech.s4vi0r.flightsearch.data.local.UserPreferencesRepository

class FlightSearchApplication : Application() {
    
    private val database: FlightSearchDatabase by lazy {
        FlightSearchDatabase.getDatabase(this)
    }
    
    val flightSearchRepository: FlightSearchRepository by lazy {
        FlightSearchRepository(
            airportDao = database.airportDao(),
            favoriteDao = database.favoriteDao()
        )
    }
    
    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(this)
    }
}
