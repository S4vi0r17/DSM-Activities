package tech.s4vi0r.mycity.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tech.s4vi0r.mycity.data.LocalPlacesDataProvider
import tech.s4vi0r.mycity.model.Place
import tech.s4vi0r.mycity.model.PlaceCategory

/**
 * ViewModel para la aplicación Mi Ciudad
 */
class MyCityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        MyCityUiState(
            allPlaces = LocalPlacesDataProvider.getAllPlaces(),
            currentPlace = LocalPlacesDataProvider.defaultPlace
        )
    )
    val uiState: StateFlow<MyCityUiState> = _uiState

    /**
     * Actualiza la categoría seleccionada y muestra los lugares de esa categoría
     */
    fun selectCategory(category: PlaceCategory) {
        val placesInCategory = LocalPlacesDataProvider.getPlacesByCategory(category)
        _uiState.update {
            it.copy(
                selectedCategory = category,
                filteredPlaces = placesInCategory,
                currentPlace = placesInCategory.firstOrNull()
                    ?: LocalPlacesDataProvider.defaultPlace,
                isShowingCategoryList = false,
                isShowingPlacesList = true
            )
        }
    }

    /**
     * Actualiza el lugar actualmente seleccionado
     */
    fun selectPlace(place: Place) {
        _uiState.update {
            it.copy(currentPlace = place)
        }
    }

    /**
     * Navega a la lista de categorías
     */
    fun navigateToCategoryList() {
        _uiState.update {
            it.copy(
                isShowingCategoryList = true,
                isShowingPlacesList = false,
                isShowingPlaceDetail = false
            )
        }
    }

    /**
     * Navega a la lista de lugares
     */
    fun navigateToPlacesList() {
        _uiState.update {
            it.copy(
                isShowingCategoryList = false,
                isShowingPlacesList = true,
                isShowingPlaceDetail = false
            )
        }
    }

    /**
     * Navega a los detalles del lugar
     */
    fun navigateToPlaceDetail() {
        _uiState.update {
            it.copy(
                isShowingCategoryList = false,
                isShowingPlacesList = false,
                isShowingPlaceDetail = true
            )
        }
    }
}

/**
 * Estado de la UI para Mi Ciudad
 */
data class MyCityUiState(
    val allPlaces: List<Place> = emptyList(),
    val filteredPlaces: List<Place> = emptyList(),
    val currentPlace: Place = LocalPlacesDataProvider.defaultPlace,
    val selectedCategory: PlaceCategory? = null,
    val isShowingCategoryList: Boolean = true,
    val isShowingPlacesList: Boolean = false,
    val isShowingPlaceDetail: Boolean = false
)
