package com.example.frontend.presentation.feature.biere

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.ui.theme.SpritzClairColor

data class Beer(
    val name: String,
    val degree: Double,
    val color: String,
    var quantity: Double,
    var prix : Double
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBiere(navHostController: NavHostController, modifier: Modifier) {

    val beerCollection = mutableMapOf<Int, Beer>()
    val scrollState = rememberScrollState()

    // Ajouter des exemples de bières
    beerCollection[0] = Beer("Pale Ale", 5.2, "Dorée", 0.25,7.0)
    beerCollection[1] = Beer("Stout", 8.0, "Ambrée", 0.5,6.5)
    beerCollection[3] = Beer("Pilsner", 4.8, "Blonde", 0.5,7.0)
    beerCollection[4] = Beer("Pale Ale", 5.2, "Blanche", 0.25,7.0)
    beerCollection[5] = Beer("Stout", 8.0, "Rousse", 0.5,6.5)
    beerCollection[6] = Beer("Pilsner", 4.8, "Brune", 0.5,7.0)
    beerCollection[7] = Beer("Pale Ale", 5.2, "Brune", 0.25,7.0)
    beerCollection[8] = Beer("Stout", 8.0, "Rubis", 0.5,6.5)
    beerCollection[9] = Beer("Pilsner", 4.8, "ébène", 0.5,7.0)
    beerCollection[10] = Beer("Pale Ale", 5.2, "Cuivrée", 0.25,7.0)
    beerCollection[11] = Beer("Stout", 8.0, "Rubis", 0.5,6.5)
    beerCollection[12] = Beer("Pilsner", 4.8, "ébène", 0.5,7.0)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight().verticalScroll(scrollState).padding( vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            beerCollection.forEach { beer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth() // La Card occupe toute la largeur de l'écran
                        .padding(8.dp), // Ajoute un peu d'espace autour de la carte
                    shape = RoundedCornerShape(8.dp), // Coins arrondis
                    colors = CardDefaults.cardColors(
                        containerColor = mapBeerColor(beer.value.color), //Card background color
                        contentColor = mapFontOverBeer(beer.value.color)  //Card content color,e.g.text
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Ajoute un espace intérieur pour le contenu
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = beer.value.name)
                            Text(text = beer.value.color)
                        }

                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Add a beer",
                                tint = mapFontOverBeer(beer.value.color),
                                )
                        }
                        Text(text = beer.value.degree.toString() + "°", modifier = Modifier.padding(12.dp))

                        Column {
                            Text(text = beer.value.prix.toString() + " €")
                            Text(text = beer.value.quantity.toString() + "L")
                        }
                    }
                }
            }

        }
    }
}