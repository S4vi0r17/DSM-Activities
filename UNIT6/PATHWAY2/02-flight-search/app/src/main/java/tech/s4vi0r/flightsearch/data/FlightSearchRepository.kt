package tech.s4vi0r.flightsearch.data

import kotlinx.coroutines.flow.Flow
import tech.s4vi0r.flightsearch.data.local.dao.AirportDao
import tech.s4vi0r.flightsearch.data.local.dao.FavoriteDao
import tech.s4vi0r.flightsearch.data.local.entity.Airport
import tech.s4vi0r.flightsearch.data.local.entity.Favorite

/**
 * Repository que maneja todas las operaciones de datos de la app
 */
class FlightSearchRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) {
    
    // Airport operations
    
    /**
     * Busca aeropuertos que coincidan con la query
     */
    fun searchAirports(query: String): Flow<List<Airport>> {
        return airportDao.searchAirports(query)
    }
    
    /**
     * Obtiene un aeropuerto por su código IATA
     */
    suspend fun getAirportByIataCode(iataCode: String): Airport? {
        return airportDao.getAirportByIataCode(iataCode)
    }
    
    /**
     * Obtiene todos los aeropuertos excepto el especificado
     * Para mostrar destinos posibles desde un aeropuerto
     */
    fun getAllAirportsExcept(excludeIataCode: String): Flow<List<Airport>> {
        return airportDao.getAllAirportsExcept(excludeIataCode)
    }
    
    /**
     * Obtiene todos los aeropuertos
     */
    fun getAllAirports(): Flow<List<Airport>> {
        return airportDao.getAllAirports()
    }
    
    // Favorite operations
    
    /**
     * Obtiene todos los vuelos favoritos
     */
    fun getAllFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }
    
    /**
     * Agrega un vuelo a favoritos
     */
    suspend fun addFavorite(departureCode: String, destinationCode: String) {
        val favorite = Favorite(
            departureCode = departureCode,
            destinationCode = destinationCode
        )
        favoriteDao.insertFavorite(favorite)
    }
    
    /**
     * Elimina un vuelo de favoritos
     */
    suspend fun removeFavorite(departureCode: String, destinationCode: String) {
        val favorite = favoriteDao.getFavorite(departureCode, destinationCode)
        favorite?.let {
            favoriteDao.deleteFavorite(it)
        }
    }
    
    /**
     * Verifica si una ruta es favorita
     */
    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean> {
        return favoriteDao.isFavorite(departureCode, destinationCode)
    }
}
