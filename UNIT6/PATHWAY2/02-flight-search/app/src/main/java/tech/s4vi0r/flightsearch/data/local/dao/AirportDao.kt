package tech.s4vi0r.flightsearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.s4vi0r.flightsearch.data.local.entity.Airport

@Dao
interface AirportDao {
    
    /**
     * Busca aeropuertos por nombre o código IATA
     * Ordena por número de pasajeros (descendente)
     */
    @Query("""
        SELECT * FROM airport 
        WHERE name LIKE '%' || :searchQuery || '%' 
        OR iata_code LIKE '%' || :searchQuery || '%'
        ORDER BY passengers DESC
    """)
    fun searchAirports(searchQuery: String): Flow<List<Airport>>
    
    /**
     * Obtiene un aeropuerto por su código IATA
     */
    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    suspend fun getAirportByIataCode(iataCode: String): Airport?
    
    /**
     * Obtiene todos los aeropuertos excepto el especificado
     * Para mostrar los destinos posibles desde un aeropuerto
     */
    @Query("""
        SELECT * FROM airport 
        WHERE iata_code != :excludeIataCode
        ORDER BY passengers DESC
    """)
    fun getAllAirportsExcept(excludeIataCode: String): Flow<List<Airport>>
    
    /**
     * Obtiene todos los aeropuertos
     */
    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>
}
