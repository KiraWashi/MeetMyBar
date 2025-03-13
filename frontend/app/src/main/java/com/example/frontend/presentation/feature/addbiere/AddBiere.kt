package com.example.frontend.presentation.feature.addbiere

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.presentation.components.Popup
import com.example.frontend.presentation.components.ColorPicker
import com.example.frontend.presentation.components.mapBeerColor
import com.example.frontend.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBiere(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<AddBiereViewModel>()
    val (name, setName) = remember { mutableStateOf("") }
    val (alcoholDegree, setAlcoholDegree) = remember { mutableStateOf("") }
    val (type, setType) = remember { mutableStateOf("") }
    val (quantity, setQuantity) = remember { mutableStateOf("") }
    val (prix, setPrix) = remember { mutableStateOf("") }
    val (brand, setBrand) = remember { mutableStateOf("") }

    val updateState by viewModel.createBiereViewModel.collectAsState()
    var showPopup by remember { mutableStateOf(false) }

    LaunchedEffect(updateState.success) {
        System.out.println(updateState);
        if (updateState.success) {
            showPopup = true
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize().background(mapBeerColor(type)),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,

                ),
                title = {
                    Text("Ajouter une boisson")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Ajouter une action pour sauvegarder les informations
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
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
            OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text(text = "Nom de la bière") },
                modifier = Modifier.fillMaxWidth()
            )
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
                    viewModel.createBeer(
                        name = name,
                        alcoholDegree = alcoholDegree,
                        brand = brand,
                        type = type
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ajouter")
            }
            if (showPopup) {
                Popup(onDismiss = {
                    showPopup = false
                    navHostController.navigate(Screen.ListBiere.route)
                },"Modification réussie","La bière a été mise à jour avec succès !")

            }
        }
    }
}
