package tech.s4vi0r.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tech.s4vi0r.flightsearch.FlightSearchApplication
import tech.s4vi0r.flightsearch.data.FlightSearchRepository
import tech.s4vi0r.flightsearch.data.local.UserPreferencesRepository
import tech.s4vi0r.flightsearch.data.local.entity.Airport
import tech.s4vi0r.flightsearch.ui.model.FlightRoute

class FlightSearchViewModel(
    private val repository: FlightSearchRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    
    // Estado interno del campo de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // Aeropuerto seleccionado para ver vuelos
    private val _selectedAirport = MutableStateFlow<Airport?>(null)
    
    // Estado de la UI
    val uiState: StateFlow<FlightSearchUiState> = combine(
        _searchQuery,
        _selectedAirport
    ) { query, selectedAirport ->
        when {
            // Si hay un aeropuerto seleccionado, mostrar sus vuelos
            selectedAirport != null -> {
                val destinations = repository.getAllAirportsExcept(selectedAirport.iataCode).first()
                val favorites = repository.getAllFavorites().first()
                
                val flights = destinations.map { destination ->
                    val isFavorite = favorites.any { fav ->
                        fav.departureCode == selectedAirport.iataCode &&
                        fav.destinationCode == destination.iataCode
                    }
                    FlightRoute(
                        departureAirport = selectedAirport,
                        destinationAirport = destination,
                        isFavorite = isFavorite
                    )
                }
                FlightSearchUiState.FlightResults(selectedAirport, flights)
            }
            // Si hay texto de búsqueda, mostrar sugerencias
            query.isNotBlank() -> {
                val suggestions = repository.searchAirports(query).first()
                FlightSearchUiState.Searching(query, suggestions)
            }
            // Si no hay búsqueda ni selección, mostrar favoritos
            else -> {
                val favorites = repository.getAllFavorites().first()
                val favoriteRoutes = favorites.mapNotNull { fav ->
                    val departure = repository.getAirportByIataCode(fav.departureCode)
                    val destination = repository.getAirportByIataCode(fav.destinationCode)
                    
                    if (departure != null && destination != null) {
                        FlightRoute(
                            departureAirport = departure,
                            destinationAirport = destination,
                            isFavorite = true
                        )
                    } else null
                }
                FlightSearchUiState.Favorites(favoriteRoutes)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FlightSearchUiState.Favorites()
    )
    
    init {
        // Cargar la búsqueda guardada al iniciar
        loadSavedSearchQuery()
    }
    
    /**
     * Carga la query de búsqueda guardada en DataStore
     */
    private fun loadSavedSearchQuery() {
        viewModelScope.launch {
            val savedQuery = userPreferencesRepository.searchQuery.first()
            if (savedQuery.isNotBlank()) {
                _searchQuery.value = savedQuery
                // Si hay una query guardada, buscar el aeropuerto y seleccionarlo
                val airports = repository.searchAirports(savedQuery).first()
                if (airports.isNotEmpty()) {
                    selectAirport(airports.first())
                }
            }
        }
    }
    
    /**
     * Actualiza el texto de búsqueda
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _selectedAirport.value = null
        
        // Guardar en DataStore
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(query)
        }
    }
    
    /**
     * Selecciona un aeropuerto de las sugerencias
     */
    fun selectAirport(airport: Airport) {
        _selectedAirport.value = airport
        _searchQuery.value = airport.iataCode
        
        // Guardar en DataStore
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(airport.iataCode)
        }
    }
    
    /**
     * Limpia la búsqueda y vuelve a mostrar favoritos
     */
    fun clearSearch() {
        _searchQuery.value = ""
        _selectedAirport.value = null
        
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery("")
        }
    }
    
    /**
     * Alterna el estado de favorito de una ruta
     */
    fun toggleFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(departureCode, destinationCode).first()
            if (isFavorite) {
                repository.removeFavorite(departureCode, destinationCode)
            } else {
                repository.addFavorite(departureCode, destinationCode)
            }
            
            // Actualizar el estado para reflejar el cambio
            _selectedAirport.value?.let { airport ->
                selectAirport(airport)
            }
        }
    }
    
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    repository = application.flightSearchRepository,
                    userPreferencesRepository = application.userPreferencesRepository
                )
            }
        }
    }
}
