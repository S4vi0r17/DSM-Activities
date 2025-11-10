package tech.s4vi0r.flightsearch.ui.model

import tech.s4vi0r.flightsearch.data.local.entity.Airport

/**
 * Representa una ruta de vuelo
 */
data class FlightRoute(
    val departureAirport: Airport,
    val destinationAirport: Airport,
    val isFavorite: Boolean = false
)
