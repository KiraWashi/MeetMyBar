package com.example.frontend.presentation.feature.bar

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontend.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import com.example.frontend.presentation.feature.biere.Beer
import com.example.frontend.presentation.feature.biere.mapBeerColor
import com.example.frontend.presentation.feature.biere.mapFontOverBeer
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.presentation.components.Caroussel
import com.example.frontend.ui.theme.SpritzClairColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageBar(modifier: Modifier, navHostController: NavHostController) {

    val beerCollection = mutableMapOf<Int, Beer>()
    beerCollection[0] = Beer("Pale Ale", 5.2, "Dorée", 0.25,7.0)
    beerCollection[1] = Beer("Stout", 8.0, "Ambrée", 0.5,6.5)

    val darkMode = !isSystemInDarkTheme();
    val scrollState = rememberScrollState()

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
                    IconButton(onClick = { navHostController.navigate(Screen.HomeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Settings",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Add a beer",
                            tint = Color.Black,
                        )
                    }
                },
            )
        },
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .verticalScroll(scrollState).padding(innerPadding),
            verticalArrangement = Arrangement.Top,
        ) {
            Box() {
                Image(
                    painter = painterResource(id = R.drawable.sharkpool), // Utilisez le nom sans extension
                    contentDescription = "Shark Pool Image",
                    contentScale = ContentScale.FillWidth, // Ajuste l'image
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = "Shark Pool",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp,
                    color = if (darkMode) Color.Black else Color.White
                ),
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = "Bar karaoké",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    color = if (darkMode) Color.Black else Color.White
                ),
                modifier = Modifier.padding(bottom = 10.dp)// Navigue vers la route
            )
            HorraireOuverture(
                modifier = Modifier.fillMaxWidth(),
                darkMode = darkMode
            )
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
                            Text(text = beer.value.degree.toString() + "°")
                            Column {
                                Text(text = beer.value.prix.toString() + " €")
                                Text(text = beer.value.quantity.toString() + "L")
                            }
                        }
                    }
                }
            Text(
                text = "Voir toutes les bières diponibles",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = if (darkMode) Color.Black else Color.White
                ),
                modifier = Modifier.padding(4.dp).clickable {
                    navHostController.navigate(Screen.ListBiere.route)
                },
            )
            Caroussel()
        }


    }
}