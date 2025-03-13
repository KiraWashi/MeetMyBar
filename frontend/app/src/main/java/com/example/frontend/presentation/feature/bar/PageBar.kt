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
import androidx.compose.runtime.collectAsState
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Add a beer",
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

                    // TODO
                    Image(
                        painter = painterResource(id = R.drawable.sharkpool),
                        contentDescription = "Shark Pool Image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
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

                // TODO
                HorraireOuverture(
                    modifier = Modifier.fillMaxWidth(),
                    darkMode = darkMode,
                    planning = bar.planning
                )
                if(!bar.drinks.isEmpty()){
                    val firstDrink = bar.drinks.get(0);
                    Card(
                        modifier = Modifier
                            .fillMaxWidth() // La Card occupe toute la largeur de l'écran
                            .padding(2.dp), // Ajoute un peu d'espace autour de la carte
                        shape = RoundedCornerShape(8.dp), // Coins arrondis
                        colors = CardDefaults.cardColors(
                            containerColor = mapBeerColor(firstDrink.type), //Card background color
                            contentColor = mapFontOverBeer(firstDrink.type)  //Card content color,e.g.text
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), // Ajoute un espace intérieur pour le contenu
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