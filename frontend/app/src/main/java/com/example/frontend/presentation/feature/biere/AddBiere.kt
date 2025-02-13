package com.example.frontend.presentation.feature.biere

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.ui.theme.SpritzClairColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBiere(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val (name, setName) = remember { mutableStateOf("") }
    val (degree, setDegree) = remember { mutableStateOf("") }
    val (selectedColor, setSelectedColor) = remember { mutableStateOf("") }
    val (quantity, setQuantity) = remember { mutableStateOf("") }
    val (prix, setPrix) = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(mapBeerColor(selectedColor)),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpritzClairColor,
                    titleContentColor = Color.Black,

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
                actions = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Valider",
                            tint = Color.Black
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
                value = degree,
                onValueChange = setDegree,
                label = { Text("Degré d'alcool") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            ColorPicker(
                selectedColor = selectedColor,
                onColorSelected = setSelectedColor
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

            Button(
                onClick = {
                    // Valider et créer une nouvelle bière
                    val newBeer = Beer(
                        name = name,
                        degree = degree.toDoubleOrNull() ?: 0.0,
                        color = selectedColor,
                        quantity = quantity.toDoubleOrNull() ?: 0.0,
                        prix = prix.toDoubleOrNull() ?: 0.0
                    )
                    // Ajouter la bière à votre système ici
                    navHostController.navigate(Screen.ListBiere.route)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ajouter")
            }
        }
    }
}
