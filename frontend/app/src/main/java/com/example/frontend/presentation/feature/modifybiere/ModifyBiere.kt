package com.example.frontend.presentation.feature.modifybiere

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.data.utils.Resource
import com.example.frontend.presentation.components.Popup
import com.example.frontend.presentation.components.ColorPicker
import com.example.frontend.presentation.components.MeetMyBarTextField
import com.example.frontend.presentation.components.mapBeerColor
import com.example.frontend.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyBiere(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    drinkId : Int,
) {
    val viewModel = koinViewModel<ModifyBiereViewModel>()
    val drinkState by viewModel.selectedBeer.collectAsState()

    val updateState by viewModel.modifyBiereState.collectAsState()
    val deleteState by viewModel.deleteBiereState.collectAsState()
    var showPopup by remember { mutableStateOf(false) }
    var showPopupDelete by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(drinkId) {
        viewModel.getDrink(drinkId)
    }

    LaunchedEffect(updateState.success) {
        if (updateState.success) {
            showPopup = true
        }
    }

    LaunchedEffect(deleteState) {
        when (deleteState) {
            is Resource.Success -> {
                showPopupDelete = true
            }
            is Resource.Error -> {
                showPopupDelete = true
            }
            else -> {}
        }
    }
    val drink = drinkState.data
    if (drinkState is Resource.Loading || drink == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Chargement...")
        }
        return
    }

    val (name, setName) = remember { mutableStateOf(drink.name) }
    val (alcoholDegree, setAlcoholDegree) = remember { mutableStateOf(drink.alcoholDegree) }
    val (type, setType) = remember { mutableStateOf(drink.type) }
    val (quantity, setQuantity) = remember { mutableStateOf("") }
    val (prix, setPrix) = remember { mutableStateOf("") }
    val (brand, setBrand) = remember { mutableStateOf(drink.brand) }



    Scaffold(
        modifier = Modifier.fillMaxSize().background(mapBeerColor(type)),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                title = {
                    Text("Modifier La boisson")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.ListBiere.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.deleteBiere(drink.id) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Supprimer",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MeetMyBarTextField(
                value = name,
                onTextFieldValueChange = setName,
                label = "Nom de la bière",
                modifier = Modifier.fillMaxWidth()
            )
            /*OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text(text = "Nom de la bière") },
                modifier = Modifier.fillMaxWidth()
            )*/
            OutlinedTextField(
                value = alcoholDegree,
                onValueChange = setAlcoholDegree,
                label = { Text("Degré d'alcool") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            ColorPicker(
                selectedColor = type,
                onColorSelected = setType
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = setQuantity,
                label = { Text("Quantité (en litres)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = prix,
                onValueChange = setPrix,
                label = { Text("Prix (€)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = brand,
                onValueChange = setBrand,
                label = { Text("Marque") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.updateBeer(id = drink.id,
                        name = name,
                        alcoholDegree = alcoholDegree,
                        brand = brand,
                        type = type
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Modifier")
            }

            if (showPopup) {
                Popup(onDismiss = {
                    showPopup = false
                    navHostController.navigate(Screen.ListBiere.route)
                },"Modification réussie","La bière a été mise à jour avec succès !")

            }
            if (showPopupDelete) {
                Popup(onDismiss = {
                    showPopup = false
                    navHostController.navigate(Screen.ListBiere.route)
                }, "Suppression réussie", "La bière a été supprimée avec succès !")
            }

            if (showError) {
                Popup(onDismiss = { showError = false }, "Erreur", "Échec de la suppression.")
            }
        }
    }
}
