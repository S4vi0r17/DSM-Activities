package tech.s4vi0r.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Modelo de datos para un lugar recomendado en la ciudad
 */
data class Place(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val shortDescription: Int,
    @StringRes val fullDescription: Int,
    val category: PlaceCategory,
    val rating: Double,
    @StringRes val address: Int,
    @DrawableRes val imageResourceId: Int,
    @DrawableRes val bannerImageResourceId: Int
)
