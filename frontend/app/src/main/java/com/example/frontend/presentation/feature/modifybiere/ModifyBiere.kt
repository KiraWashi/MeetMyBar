package com.example.frontend.presentation.feature.modifybiere

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.data.utils.Resource
import com.example.frontend.presentation.components.ColorPicker
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarTextField
import com.example.frontend.presentation.components.SuccessDialog
import com.example.frontend.presentation.components.mapBeerColor
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyBiere(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    drinkId: Int,
    barId: Int,
    volume: Float,
    price:Float
) {
    val viewModel = koinViewModel<ModifyBiereViewModel>()
    val drinkState by viewModel.selectedBeer.collectAsState()

    val updateState by viewModel.modifyBiereState.collectAsState()
    val deleteBarDrinkResult by viewModel.deleteBarDrinkResult.collectAsState()

    var showUpdateSuccess by remember { mutableStateOf(false) }
    var showDeleteSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(drinkId) {
        viewModel.getDrink(drinkId)
    }

    LaunchedEffect(updateState.success) {
        if (updateState.success) {
            showUpdateSuccess = true
        }
    }

    LaunchedEffect(deleteBarDrinkResult) {
        when (deleteBarDrinkResult) {
            is Resource.Success -> {
                showDeleteSuccess = true
            }
            is Resource.Error -> {
                showError = true
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

    // Pour les champs qui doivent être affichés mais non modifiables
    val name = drink.name
    val alcoholDegree = drink.alcoholDegree
    val type = drink.type
    val brand = drink.brand

    // Pour les champs modifiables (prix et volume)
    val (quantity, setQuantity) = remember { mutableStateOf(volume.toString()) }
    val (prix, setPrix) = remember { mutableStateOf(price.toString()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mapBeerColor(type)),
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
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                actions = {
                    // Bouton de suppression direct sans confirmation
                    IconButton(onClick = {
                        viewModel.deleteBarDrinkLink(
                            idBar = barId,
                            idDrink = drinkId,
                            volume = volume.toInt()
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Supprimer le lien"
                        )
                    }
                }
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
            // Champs en lecture seule (non modifiables)
            MeetMyBarTextField(
                value = name,
                onTextFieldValueChange = { },
                label = "Nom de la bière",
                enabled = false,
            )

            MeetMyBarTextField(
                value = alcoholDegree,
                onTextFieldValueChange = { },
                label = "Degré d'alcool",
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )

            ColorPicker(
                selectedColor = type,
                onColorSelected = {},
                enabled = false,
            )

            MeetMyBarTextField(
                value = brand,
                onTextFieldValueChange = { },
                label = "Marque",
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )

            // Champs modifiables (prix et volume)
            MeetMyBarTextField(
                value = quantity,
                onTextFieldValueChange = setQuantity,
                label = "Quantité (en cl)",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
            )

            MeetMyBarTextField(
                value = prix,
                onTextFieldValueChange = setPrix,
                label = "Prix (€)",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth()
            )

            MeetMyBarButton(
                text = "Mettre à jour",
                modifier = Modifier.align(Alignment.End)
            ) {
                viewModel.updateDrinkPrice(
                    idBar = barId,
                    idDrink = drinkId,
                    volume = quantity,
                    newPrice = prix
                )
            }

            // Dialogues pour les résultats
            if (showUpdateSuccess) {
                SuccessDialog(
                    successMessage = "La boisson a été mise à jour avec succès !",
                    onDismissDialog = {
                        showUpdateSuccess = false
                        navHostController.popBackStack()
                    }
                )
            }

            if (showDeleteSuccess) {
                SuccessDialog(
                    successMessage = "Cette boisson a été supprimée du bar !",
                    onDismissDialog = {
                        showDeleteSuccess = false
                        viewModel.resetDeleteBarDrinkLinkState()
                        navHostController.popBackStack()
                    }
                )
            }

            if (showError) {
                ErrorDialog(
                    onDismissDialog = {
                        showError = false
                    }
                )
            }
        }
    }
}