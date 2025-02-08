package com.example.frontend.presentation.biere

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.ui.theme.SpritzClairColor
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBiere(navHostController: NavHostController, modifier: Modifier) {

    val viewModel = koinViewModel<ListBiereViewModel>()
    val homeViewModelState = viewModel.listeBiereViewModelState.collectAsState()

    viewModel.getDrinks() //appel api

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpritzClairColor,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("The Shark Pool")
                },
                navigationIcon = {
                    IconButton(onClick = {navHostController.navigate(Screen.PageBar.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {navHostController.navigate(Screen.AddBiere.route)}) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add a beer",
                            tint = Color.Black,
                        )
                    }
                },
            )
        },
    ) {
        homeViewModelState.value.drinks?.let { drinksList ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight().padding( vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn{
                    items(drinksList) { beer ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth() // La Card occupe toute la largeur de l'écran
                                .padding(8.dp), // Ajoute un peu d'espace autour de la carte
                            shape = RoundedCornerShape(8.dp), // Coins arrondis
                            colors = CardDefaults.cardColors(
                                containerColor = mapBeerColor(beer.type), //Card background color
                                contentColor = mapFontOverBeer(beer.type)  //Card content color,e.g.text
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp), // Ajoute un espace intérieur pour le contenu
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
                                Text(text = beer.alcoholDegree.toString() + "°", modifier = Modifier.padding(12.dp))

                                Column {
                                    Text(text = "6" + " €")
                                    Text(text = "0.5" + "L")
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}