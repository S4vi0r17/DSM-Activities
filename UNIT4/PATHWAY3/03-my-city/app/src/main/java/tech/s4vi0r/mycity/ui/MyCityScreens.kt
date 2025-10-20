package tech.s4vi0r.mycity.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.s4vi0r.mycity.R
import tech.s4vi0r.mycity.data.LocalPlacesDataProvider
import tech.s4vi0r.mycity.model.Place
import tech.s4vi0r.mycity.model.PlaceCategory
import tech.s4vi0r.mycity.ui.theme.MyCityTheme
import tech.s4vi0r.mycity.utils.MyCityContentType

/**
 * Composable principal que maneja la navegación y el diseño adaptable
 */
@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val viewModel: MyCityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> MyCityContentType.ListOnly

        WindowWidthSizeClass.Expanded -> MyCityContentType.ListAndDetail
        else -> MyCityContentType.ListOnly
    }

    Scaffold(
        topBar = {
            MyCityAppBar(
                currentScreen = when {
                    uiState.isShowingCategoryList -> stringResource(R.string.categories_screen_title)
                    uiState.isShowingPlacesList -> stringResource(R.string.places_screen_title)
                    else -> stringResource(R.string.place_detail_screen_title)
                }, canNavigateBack = !uiState.isShowingCategoryList, onBackClick = {
                    when {
                        uiState.isShowingPlaceDetail -> viewModel.navigateToPlacesList()
                        uiState.isShowingPlacesList -> viewModel.navigateToCategoryList()
                    }
                }, windowSize = windowSize, uiState = uiState
            )
        }) { innerPadding ->
        if (contentType == MyCityContentType.ListAndDetail) {
            // Diseño para pantallas grandes (tabletas)
            MyCityListAndDetail(
                uiState = uiState,
                onCategoryClick = { category ->
                    viewModel.selectCategory(category)
                },
                onPlaceClick = { place ->
                    viewModel.selectPlace(place)
                },
                onBackPressed = onBackPressed,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // Diseño para pantallas pequeñas (teléfonos)
            when {
                uiState.isShowingCategoryList -> {
                    CategoryListScreen(
                        onCategoryClick = { category ->
                            viewModel.selectCategory(category)
                        }, contentPadding = innerPadding
                    )
                }

                uiState.isShowingPlacesList -> {
                    PlacesListScreen(places = uiState.filteredPlaces, onPlaceClick = { place ->
                        viewModel.selectPlace(place)
                        viewModel.navigateToPlaceDetail()
                    }, contentPadding = innerPadding, onBackPressed = {
                        viewModel.navigateToCategoryList()
                    })
                }

                uiState.isShowingPlaceDetail -> {
                    PlaceDetailScreen(
                        place = uiState.currentPlace,
                        contentPadding = innerPadding,
                        onBackPressed = {
                            viewModel.navigateToPlacesList()
                        })
                }
            }
        }
    }
}

/**
 * Barra superior de la aplicación
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    windowSize: WindowWidthSizeClass,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {
    // Mostrar botón de retroceso cuando no estemos en la lista de categorías
    val showBackButton = canNavigateBack

    TopAppBar(
        title = {
        Text(
            text = currentScreen, fontWeight = FontWeight.Bold
        )
    }, navigationIcon = if (showBackButton) {
        {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    } else {
        { Box {} }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    ), modifier = modifier)
}

/**
 * Pantalla de lista de categorías
 */
@Composable
fun CategoryListScreen(
    onCategoryClick: (PlaceCategory) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.select_category),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            items(PlaceCategory.values()) { category ->
                CategoryCard(
                    category = category, onClick = { onCategoryClick(category) })
            }
        }
    }
}

/**
 * Tarjeta de categoría
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: PlaceCategory, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.category_card_height)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(getCategoryIcon(category)),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.category_icon_size)),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(category.categoryName),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Pantalla de lista de lugares
 */
@Composable
fun PlacesListScreen(
    places: List<Place>,
    onPlaceClick: (Place) -> Unit,
    contentPadding: PaddingValues,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }

    LazyColumn(
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + dimensionResource(R.dimen.padding_medium),
            start = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_medium)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
    ) {
        items(places, key = { place -> place.id }) { place ->
            PlaceListItem(
                place = place, onItemClick = onPlaceClick
            )
        }
    }
}

/**
 * Item de la lista de lugares
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListItem(
    place: Place, onItemClick: (Place) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation)),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(place) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.card_image_height))
        ) {
            Image(
                painter = painterResource(place.imageResourceId),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(place.name),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(place.shortDescription),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${place.rating}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Pantalla de detalle del lugar
 */
@Composable
fun PlaceDetailScreen(
    place: Place,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }

    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier.padding(
                    bottom = contentPadding.calculateBottomPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            // Banner con imagen
            Box {
                Image(
                    painter = painterResource(place.bannerImageResourceId),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.banner_height))
                )
                // Gradiente oscuro en la parte inferior
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim), 0f, 400f
                            )
                        )
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = stringResource(place.name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = " ${place.rating}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Contenido del detalle
            Column(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal),
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical)
                )
            ) {
                // Dirección
                Text(
                    text = stringResource(R.string.address_label),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(place.address),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )

                // Descripción completa
                Text(
                    text = stringResource(place.fullDescription),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

/**
 * Diseño de lista y detalle para pantallas grandes
 */
@Composable
fun MyCityListAndDetail(
    uiState: MyCityUiState,
    onCategoryClick: (PlaceCategory) -> Unit,
    onPlaceClick: (Place) -> Unit,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        // Panel izquierdo: Lista de categorías o lugares
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_small)
                )
        ) {
            if (uiState.isShowingCategoryList || uiState.selectedCategory == null) {
                CategoryListScreen(
                    onCategoryClick = onCategoryClick, contentPadding = PaddingValues(0.dp)
                )
            } else {
                PlacesListScreen(
                    places = uiState.filteredPlaces,
                    onPlaceClick = onPlaceClick,
                    contentPadding = PaddingValues(0.dp),
                    onBackPressed = onBackPressed
                )
            }
        }

        // Panel derecho: Detalle del lugar
        Box(
            modifier = Modifier
                .weight(3f)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        ) {
            if (uiState.selectedCategory != null) {
                PlaceDetailScreen(
                    place = uiState.currentPlace,
                    contentPadding = PaddingValues(0.dp),
                    onBackPressed = onBackPressed
                )
            } else {
                // Mensaje cuando no hay selección
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.select_category),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Función auxiliar para obtener el icono de cada categoría
 */
fun getCategoryIcon(category: PlaceCategory): Int {
    return when (category) {
        PlaceCategory.CAFETERIAS -> R.drawable.ic_cafe
        PlaceCategory.RESTAURANTES -> R.drawable.ic_restaurant
        PlaceCategory.PARQUES -> R.drawable.ic_park
        PlaceCategory.MUSEOS -> R.drawable.ic_museum
        PlaceCategory.CENTROS_COMERCIALES -> R.drawable.ic_mall
    }
}

// Previews
@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    MyCityTheme {
        CategoryCard(
            category = PlaceCategory.CAFETERIAS, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceListItemPreview() {
    MyCityTheme {
        PlaceListItem(
            place = LocalPlacesDataProvider.defaultPlace, onItemClick = {})
    }
}
