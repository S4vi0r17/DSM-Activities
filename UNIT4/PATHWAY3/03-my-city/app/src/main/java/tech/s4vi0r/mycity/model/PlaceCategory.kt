package tech.s4vi0r.mycity.model

import androidx.annotation.StringRes
import tech.s4vi0r.mycity.R

/**
 * Categorías de lugares disponibles en la ciudad
 */
enum class PlaceCategory(@StringRes val categoryName: Int) {
    CAFETERIAS(R.string.category_cafeterias),
    RESTAURANTES(R.string.category_restaurantes),
    PARQUES(R.string.category_parques),
    MUSEOS(R.string.category_museos),
    CENTROS_COMERCIALES(R.string.category_centros_comerciales)
}
