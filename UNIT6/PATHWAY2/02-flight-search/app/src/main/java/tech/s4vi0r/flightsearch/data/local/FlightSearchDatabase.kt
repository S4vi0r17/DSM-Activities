package tech.s4vi0r.flightsearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import tech.s4vi0r.flightsearch.data.local.dao.AirportDao
import tech.s4vi0r.flightsearch.data.local.dao.FavoriteDao
import tech.s4vi0r.flightsearch.data.local.entity.Airport
import tech.s4vi0r.flightsearch.data.local.entity.Favorite

@Database(
    entities = [Airport::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FlightSearchDatabase : RoomDatabase() {
    
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        @Volatile
        private var INSTANCE: FlightSearchDatabase? = null
        
        fun getDatabase(context: Context): FlightSearchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlightSearchDatabase::class.java,
                    "flight_search.db"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insertar datos iniciales de aeropuertos
            val airports = listOf(
                "INSERT INTO airport VALUES (1, 'LIM', 'Jorge Chávez International Airport', 23250000)",
                "INSERT INTO airport VALUES (2, 'CUZ', 'Alejandro Velasco Astete International Airport', 4500000)",
                "INSERT INTO airport VALUES (3, 'AQP', 'Rodríguez Ballón International Airport', 2200000)",
                "INSERT INTO airport VALUES (4, 'TRU', 'Capitán FAP Carlos Martínez de Pinillos International Airport', 1800000)",
                "INSERT INTO airport VALUES (5, 'PIU', 'Capitán FAP Guillermo Concha Iberico International Airport', 900000)",
                "INSERT INTO airport VALUES (6, 'IQT', 'Crnl. FAP Francisco Secada Vignetta International Airport', 850000)",
                "INSERT INTO airport VALUES (7, 'JUL', 'Inca Manco Cápac International Airport', 700000)",
                "INSERT INTO airport VALUES (8, 'TCQ', 'Coronel FAP Carlos Ciriani Santa Rosa International Airport', 650000)",
                "INSERT INTO airport VALUES (9, 'TPP', 'Cadete FAP Guillermo del Castillo Paredes Airport', 450000)",
                "INSERT INTO airport VALUES (10, 'CHM', 'Tnte. FAP Jaime A. De Montreuil Morales Airport', 350000)"
            )
            
            airports.forEach { sql ->
                try {
                    db.execSQL(sql)
                } catch (e: Exception) {
                    // Si hay error, ignorar (puede ser que ya exista)
                }
            }
        }
    }
}
