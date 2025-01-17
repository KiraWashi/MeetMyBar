package com.example.frontend.presentation.biere

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme

//Todo à supprimer : c'est un exemple de récupération des bières de l'API

@Composable
fun TestApiScreen() {
    val viewModel = koinViewModel<ListBiereViewModel>()
    val homeViewModelState = viewModel.listeBiereViewModelState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Button(onClick = {
                viewModel.getDrinks()
            }) {
                Text(text = "Afficher l'alcooool")
            }
            homeViewModelState.value.drinks?.let { drinksList ->
                LazyColumn {
                    items(drinksList) { drink ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = drink.name,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Text(
                                    text = drink.brand,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "${drink.alcoholDegree}°",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = drink.type,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}