package com.example.frontend.presentation.feature.listebiere

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.presentation.components.mapBeerColor
import com.example.frontend.presentation.components.mapFontOverBeer
import com.example.frontend.presentation.components.mapNameBeer
import com.example.frontend.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "ListBiereComposable"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBiere(
    barId: Int,
    navHostController: NavHostController,
    modifier: Modifier
) {
    // Obtenir le ViewModel
    val viewModel = koinViewModel<ListBiereViewModel>()

    // Observer l'état (collecte l'état comme un State<T>)
    val state by viewModel.listeBiereViewModelState.collectAsState()

    // Log pour débogage
    LaunchedEffect(barId) {
        viewModel.getDrinks(barId = barId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                title = {
                    Text("Liste des bières")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(
                            Screen.PageBar.createRoute(barId)
                        )
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navHostController.navigate(
                            Screen.AddDrinkScreen.createRoute(barId)
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add a beer",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val drinksList = state.drinks
                if (drinksList.isEmpty()) {
                    // Afficher un message si la liste est vide
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Aucune bière disponible pour ce bar")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(drinksList) { beer ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = mapBeerColor(beer.type),
                                    contentColor = mapFontOverBeer(beer.type)
                                ),
                                onClick = {
                                    navHostController.navigate(
                                        Screen.ModifyBiere.createRoute(
                                            beer.id,
                                            barId,
                                            beer.volume.toString(),
                                            beer.price,
                                        )
                                    )
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = beer.name)
                                        Text(text = mapNameBeer(beer.type))
                                    }

                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Filled.FavoriteBorder,
                                            contentDescription = "Add a beer",
                                            tint = mapFontOverBeer(beer.type),
                                        )
                                    }
                                    Text(
                                        text = beer.alcoholDegree + "°",
                                        modifier = Modifier.padding(12.dp)
                                    )

                                    Column {
                                        Text(text = beer.price.toString() + " €")
                                        Text(text = beer.volume.toString() + "cl")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}