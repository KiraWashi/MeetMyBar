package com.example.frontend.presentation.feature.adddrink

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.example.frontend.R
import com.example.frontend.presentation.components.ColorPicker
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarTextField
import com.example.frontend.presentation.components.SuccessDialog
import com.example.frontend.presentation.feature.addbar.AddBarScreenStatus
import com.example.frontend.presentation.feature.home.HomeStatus
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDrinkScreen(
    navHostController: NavHostController,
    barId: Int,
) {
    val viewModel = koinViewModel<AddDrinkViewModel>()
    val state = viewModel.addDrinkViewModelState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDrinks()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,

                    ),
                title = {
                    Text("Ajouter une bière")
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
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = state.value.selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.secondary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[state.value.selectedTabIndex]),
                        color = MaterialTheme.colorScheme.secondary,
                        height = 3.dp
                    )
                }
            ) {
                state.value.tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = state.value.selectedTabIndex == index,
                        onClick = {
                            viewModel.onTabIndexChange(index)
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (state.value.selectedTabIndex == index)
                                    MaterialTheme.colorScheme.secondary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.secondary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            when (state.value.selectedTabIndex) {
                0 -> AddExixtingBeerScreen(
                    state = state,
                    viewModel = viewModel,
                    barId = barId,
                )

                1 -> AddNewBeerScreen(
                    state = state,
                    viewModel = viewModel,
                    barId = barId
                )
            }

            when (state.value.addDrinkStatus) {
                HomeStatus.ERROR ->
                    ErrorDialog(onDismissDialog = { navHostController.navigateUp() })

                HomeStatus.SUCCESS ->
                    SuccessDialog(
                        successMessage = "La boisson a ete ajoute avec succès",
                        onDismissDialog = { navHostController.navigateUp() }
                    )

                HomeStatus.LOADING -> {}
            }
        }
    }
}

@Composable
fun AddExixtingBeerScreen(
    state: State<AddDrinkViewModelState>,
    viewModel: AddDrinkViewModel,
    barId: Int,
) {
    var showDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showDropdown = true },
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = state.value.selectedDrink?.name
                                ?: "Sélectionnez un type de bière"
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dérouler"
                    )
                }
            }

            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                state.value.drinks?.forEach { drink ->
                    DropdownMenuItem(
                        text = { Text(drink.name) },
                        onClick = {
                            viewModel.onSelectedDrinkChange(drink)
                            showDropdown = false
                        }
                    )
                }
            }
        }

        MeetMyBarTextField(
            label = "Prix ( € )",
            value = state.value.priceTextField,
            onTextFieldValueChange = { newPrice ->
                viewModel.onPriceTextFieldChange(newPrice)
            },
        )

        MeetMyBarTextField(
            label = "Volume ( en litre )",
            value = state.value.volumeTextField,
            onTextFieldValueChange = { newVolume ->
                viewModel.onVolumeTextFieldChange(newVolume)
            },
        )
        // Spacer(modifier = Modifier.height(16.dp))
        MeetMyBarButton(
            text = "Ajouter",
            modifier = Modifier.align(Alignment.End)
        ) {
            viewModel.addDrinkToBar(barId = barId)
        }
    }
}

@Composable
fun AddNewBeerScreen(
    state: State<AddDrinkViewModelState>,
    viewModel: AddDrinkViewModel,
    barId: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MeetMyBarTextField(
            value = state.value.nameTextField,
            onTextFieldValueChange = { newValue ->
                viewModel.onNameTextFieldChange(newValue)
            },
            label =  "Nom de la bière",
        )
        MeetMyBarTextField(
            value = state.value.alcoholDegreeTextField,
            onTextFieldValueChange = { newValue ->
                viewModel.onAlcoholDegreeTextFieldChange(newValue)
            },
            label =  "Degré d'alcool",
        )

        ColorPicker(
            selectedColor = state.value.selectedBeerColorTextField,
            onColorSelected = { newValue ->
                viewModel.onSelectedBeerColorTextFieldChange(newValue)
            }
        )
        MeetMyBarTextField(
            label = "Volume ( en litre )",
            value = state.value.volumeTextField,
            onTextFieldValueChange = { newVolume ->
                viewModel.onVolumeTextFieldChange(newVolume)
            },
        )
        MeetMyBarTextField(
            label = "Prix ( € )",
            value = state.value.priceTextField,
            onTextFieldValueChange = { newPrice ->
                viewModel.onPriceTextFieldChange(newPrice)
            },
        )
        MeetMyBarTextField(
            label = "Marque",
            value = state.value.brandTextField,
            onTextFieldValueChange = { newValue ->
                viewModel.onBrandTextFieldChange(newValue)
            },
        )

        MeetMyBarButton(
            text = "Ajouter",
            modifier = Modifier.align(Alignment.End)
        ) {
            viewModel.createBeer(idBar = barId)
        }
    }
}
