package com.example.frontend.presentation.feature.bar

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material3.CircularProgressIndicator
import com.example.frontend.presentation.feature.photo.PhotoViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontend.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.presentation.components.Caroussel
import com.example.frontend.presentation.components.mapBeerColor
import com.example.frontend.presentation.components.mapFontOverBeer
import org.koin.androidx.compose.koinViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageBar(
    modifier: Modifier,
    navHostController: NavHostController,
    barId: Int
) {
    val viewModel = koinViewModel<BarScreenViewModel>()
    val homeViewModelState = viewModel.barScreenViewModelState.collectAsState()
    viewModel.getBarsById(barId)

    val darkMode = !isSystemInDarkTheme();
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                title = {
                    Text(homeViewModelState.value.bar?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(Screen.HomeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Settings",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navHostController.navigate(
                            Screen.ModifyBar.createRoute(
                                homeViewModelState.value.bar?.id ?: -1
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Modify bar",
                        )
                    }
                },
            )
        },
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .fillMaxHeight()
                .verticalScroll(scrollState).padding(innerPadding),
            verticalArrangement = Arrangement.Top,
        ) {
            homeViewModelState.value.bar?.let { bar ->
                Box() {
                    // Utiliser PhotoViewModel pour charger la photo principale du bar
                    val photoViewModel = koinViewModel<PhotoViewModel>()
                    val photos by photoViewModel.photos.collectAsState()
                    val isLoading by photoViewModel.isLoading.collectAsState()

                    // Charger les photos du bar lors de la composition initiale
                    LaunchedEffect(bar.id) {
                        photoViewModel.getPhotosByBar(bar.id)
                    }

                    // Si des photos sont disponibles, utiliser la première comme photo principale
                    if (photos.isNotEmpty() && !isLoading) {
                        val mainPhotoId = photos.first().id
                        val photoState = photoViewModel.getPhotoState(mainPhotoId).collectAsState().value

                        LaunchedEffect(mainPhotoId) {
                            photoViewModel.loadPhoto(mainPhotoId)
                        }

                        if (photoState.isLoading) {
                            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        } else if (photoState.photo != null) {
                            Image(
                                bitmap = photoState.photo.asImageBitmap(),
                                contentDescription = "${bar.name} Image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            // Utiliser l'image par défaut si la photo ne peut pas être chargée
                            Image(
                                painter = painterResource(id = R.drawable.default_bar_image),
                                contentDescription = "${bar.name} Image (Default)",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        // Aucune photo disponible, utiliser l'image par défaut
                        Image(
                            painter = painterResource(id = R.drawable.default_bar_image),
                            contentDescription = "${bar.name} Image (Default)",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = bar.name,
                    style = TextStyle(
                        fontSize = 24.sp,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = bar.address + ", " + bar.postalCode + " " + bar.city,
                    style = TextStyle(
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.padding( bottom = 10.dp)
                )

                HorraireOuverture(
                    modifier = Modifier.fillMaxWidth(),
                    darkMode = darkMode,
                    planning = bar.planning
                )
                if(!bar.drinks.isEmpty()){
                    val firstDrink = bar.drinks.get(0);
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = mapBeerColor(firstDrink.type),
                            contentColor = mapFontOverBeer(firstDrink.type)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = firstDrink.brand)
                                Text(text = firstDrink.name)
                            }
                            Text(text = firstDrink.alcoholDegree + "°")
                            Column {
                                Text(text = firstDrink.price.toString() + " €")
                                Text(text = firstDrink.volume.toString() + "L")
                            }
                        }
                    }
                }
                Text(
                    text = "Voir toutes les bières diponibles",
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(4.dp).clickable {
                        navHostController.navigate(
                            Screen.ListBiere.createRoute(barId)
                        )
                    },
                )
                Caroussel(bar.id)
            }
        }
    }
}