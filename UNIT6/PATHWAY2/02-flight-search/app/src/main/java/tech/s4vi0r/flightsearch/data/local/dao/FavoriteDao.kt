package tech.s4vi0r.flightsearch.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.s4vi0r.flightsearch.data.local.entity.Favorite

@Dao
interface FavoriteDao {
    
    /**
     * Inserta un vuelo favorito
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)
    
    /**
     * Elimina un vuelo favorito
     */
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
    
    /**
     * Obtiene todos los vuelos favoritos
     */
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>
    
    /**
     * Verifica si una ruta es favorita
     */
    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM favorite 
            WHERE departure_code = :departureCode 
            AND destination_code = :destinationCode
        )
    """)
    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>
    
    /**
     * Obtiene un favorito específico por códigos de aeropuerto
     */
    @Query("""
        SELECT * FROM favorite 
        WHERE departure_code = :departureCode 
        AND destination_code = :destinationCode
        LIMIT 1
    """)
    suspend fun getFavorite(departureCode: String, destinationCode: String): Favorite?
}
