package tech.s4vi0r.flightsearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int,
    
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "passengers")
    val passengers: Int
)
