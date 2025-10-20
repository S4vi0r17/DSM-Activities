package tech.s4vi0r.mycity.data

import tech.s4vi0r.mycity.R
import tech.s4vi0r.mycity.model.Place
import tech.s4vi0r.mycity.model.PlaceCategory

/**
 * Proveedor de datos local para lugares recomendados en Lima
 */
object LocalPlacesDataProvider {

    val defaultPlace = getAllPlaces()[0]

    fun getAllPlaces(): List<Place> {
        return listOf(
            // Cafeterías
            Place(
                id = 1,
                name = R.string.place_cafe_tostado,
                shortDescription = R.string.place_cafe_tostado_short,
                fullDescription = R.string.place_cafe_tostado_full,
                category = PlaceCategory.CAFETERIAS,
                rating = 4.7,
                address = R.string.place_cafe_tostado_address,
                imageResourceId = R.drawable.ic_cafe,
                bannerImageResourceId = R.drawable.ic_cafe_banner
            ), Place(
                id = 2,
                name = R.string.place_cafe_bisetti,
                shortDescription = R.string.place_cafe_bisetti_short,
                fullDescription = R.string.place_cafe_bisetti_full,
                category = PlaceCategory.CAFETERIAS,
                rating = 4.6,
                address = R.string.place_cafe_bisetti_address,
                imageResourceId = R.drawable.cafe_bisetti,
                bannerImageResourceId = R.drawable.cafe_bisetti_banner
            ), Place(
                id = 3,
                name = R.string.place_cafe_juan_valdez,
                shortDescription = R.string.place_cafe_juan_valdez_short,
                fullDescription = R.string.place_cafe_juan_valdez_full,
                category = PlaceCategory.CAFETERIAS,
                rating = 4.5,
                address = R.string.place_cafe_juan_valdez_address,
                imageResourceId = R.drawable.juan_valdez,
                bannerImageResourceId = R.drawable.juan_valdez_banner
            ),

            // Restaurantes
            Place(
                id = 4,
                name = R.string.place_rest_central,
                shortDescription = R.string.place_rest_central_short,
                fullDescription = R.string.place_rest_central_full,
                category = PlaceCategory.RESTAURANTES,
                rating = 4.9,
                address = R.string.place_rest_central_address,
                imageResourceId = R.drawable.central_rest,
                bannerImageResourceId = R.drawable.central_rest_banner
            ), Place(
                id = 5,
                name = R.string.place_rest_maido,
                shortDescription = R.string.place_rest_maido_short,
                fullDescription = R.string.place_rest_maido_full,
                category = PlaceCategory.RESTAURANTES,
                rating = 4.8,
                address = R.string.place_rest_maido_address,
                imageResourceId = R.drawable.maido,
                bannerImageResourceId = R.drawable.maido_banner
            ), Place(
                id = 6,
                name = R.string.place_rest_astrid_gaston,
                shortDescription = R.string.place_rest_astrid_gaston_short,
                fullDescription = R.string.place_rest_astrid_gaston_full,
                category = PlaceCategory.RESTAURANTES,
                rating = 4.8,
                address = R.string.place_rest_astrid_gaston_address,
                imageResourceId = R.drawable.astrid_gaston,
                bannerImageResourceId = R.drawable.astrid_gaston_banner
            ),

            // Parques
            Place(
                id = 7,
                name = R.string.place_park_kennedy,
                shortDescription = R.string.place_park_kennedy_short,
                fullDescription = R.string.place_park_kennedy_full,
                category = PlaceCategory.PARQUES,
                rating = 4.4,
                address = R.string.place_park_kennedy_address,
                imageResourceId = R.drawable.ic_park,
                bannerImageResourceId = R.drawable.ic_park_banner
            ), Place(
                id = 8,
                name = R.string.place_park_amor,
                shortDescription = R.string.place_park_amor_short,
                fullDescription = R.string.place_park_amor_full,
                category = PlaceCategory.PARQUES,
                rating = 4.6,
                address = R.string.place_park_amor_address,
                imageResourceId = R.drawable.ic_park,
                bannerImageResourceId = R.drawable.ic_park_banner
            ), Place(
                id = 9,
                name = R.string.place_park_reserva,
                shortDescription = R.string.place_park_reserva_short,
                fullDescription = R.string.place_park_reserva_full,
                category = PlaceCategory.PARQUES,
                rating = 4.5,
                address = R.string.place_park_reserva_address,
                imageResourceId = R.drawable.ic_park,
                bannerImageResourceId = R.drawable.ic_park_banner
            ),

            // Museos
            Place(
                id = 10,
                name = R.string.place_museum_larco,
                shortDescription = R.string.place_museum_larco_short,
                fullDescription = R.string.place_museum_larco_full,
                category = PlaceCategory.MUSEOS,
                rating = 4.7,
                address = R.string.place_museum_larco_address,
                imageResourceId = R.drawable.ic_museum,
                bannerImageResourceId = R.drawable.ic_museum_banner
            ), Place(
                id = 11,
                name = R.string.place_museum_mali,
                shortDescription = R.string.place_museum_mali_short,
                fullDescription = R.string.place_museum_mali_full,
                category = PlaceCategory.MUSEOS,
                rating = 4.6,
                address = R.string.place_museum_mali_address,
                imageResourceId = R.drawable.ic_museum,
                bannerImageResourceId = R.drawable.ic_museum_banner
            ), Place(
                id = 12,
                name = R.string.place_museum_nacion,
                shortDescription = R.string.place_museum_nacion_short,
                fullDescription = R.string.place_museum_nacion_full,
                category = PlaceCategory.MUSEOS,
                rating = 4.5,
                address = R.string.place_museum_nacion_address,
                imageResourceId = R.drawable.ic_museum,
                bannerImageResourceId = R.drawable.ic_museum_banner
            ),

            // Centros Comerciales
            Place(
                id = 13,
                name = R.string.place_mall_jockey_plaza,
                shortDescription = R.string.place_mall_jockey_plaza_short,
                fullDescription = R.string.place_mall_jockey_plaza_full,
                category = PlaceCategory.CENTROS_COMERCIALES,
                rating = 4.5,
                address = R.string.place_mall_jockey_plaza_address,
                imageResourceId = R.drawable.ic_mall,
                bannerImageResourceId = R.drawable.ic_mall_banner
            ), Place(
                id = 14,
                name = R.string.place_mall_larcomar,
                shortDescription = R.string.place_mall_larcomar_short,
                fullDescription = R.string.place_mall_larcomar_full,
                category = PlaceCategory.CENTROS_COMERCIALES,
                rating = 4.7,
                address = R.string.place_mall_larcomar_address,
                imageResourceId = R.drawable.ic_mall,
                bannerImageResourceId = R.drawable.ic_mall_banner
            ), Place(
                id = 15,
                name = R.string.place_mall_real_plaza,
                shortDescription = R.string.place_mall_real_plaza_short,
                fullDescription = R.string.place_mall_real_plaza_full,
                category = PlaceCategory.CENTROS_COMERCIALES,
                rating = 4.4,
                address = R.string.place_mall_real_plaza_address,
                imageResourceId = R.drawable.ic_mall,
                bannerImageResourceId = R.drawable.ic_mall_banner
            )
        )
    }

    fun getPlacesByCategory(category: PlaceCategory): List<Place> {
        return getAllPlaces().filter { it.category == category }
    }
}
