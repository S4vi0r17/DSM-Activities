package tech.s4vi0r.flightsearch.ui

import tech.s4vi0r.flightsearch.data.local.entity.Airport
import tech.s4vi0r.flightsearch.ui.model.FlightRoute

/**
 * Estado de la UI de la pantalla principal
 */
sealed interface FlightSearchUiState {
    /**
     * Estado inicial o cuando la búsqueda está vacía
     * Muestra los favoritos del usuario
     */
    data class Favorites(
        val favorites: List<FlightRoute> = emptyList()
    ) : FlightSearchUiState
    
    /**
     * Estado cuando el usuario está escribiendo en el campo de búsqueda
     * Muestra sugerencias de autocompletar
     */
    data class Searching(
        val searchQuery: String = "",
        val suggestions: List<Airport> = emptyList()
    ) : FlightSearchUiState
    
    /**
     * Estado cuando se selecciona un aeropuerto
     * Muestra todos los vuelos posibles desde ese aeropuerto
     */
    data class FlightResults(
        val departureAirport: Airport,
        val flights: List<FlightRoute> = emptyList()
    ) : FlightSearchUiState
}
