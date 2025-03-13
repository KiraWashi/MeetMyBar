package com.example.frontend.presentation.feature.deletetypebiere

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.R
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.frontend.domain.model.DrinkTypeModel
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarLoader
import com.example.frontend.presentation.components.MeetMyBarSecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTypeBiereScreen(
    navHostController: NavHostController,
) {
    val viewModel = koinViewModel<DeleteTypeBiereViewModel>()
    val state = viewModel.deleteTypeBiereViewModelState.collectAsState()

    // Initialiser la récupération des types de bières
    LaunchedEffect(Unit) {
        viewModel.initScreen()
    }

    // Gérer les états du ViewModel
    when (state.value.status) {
        DeleteTypeBiereScreenStatus.SUCCESS -> {
            LaunchedEffect(Unit) {
                // Recharger la liste après suppression réussie
                viewModel.initScreen()
            }
        }
        else -> {}
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.inversePrimary,
                ),
                title = {
                    Text(text = stringResource(id = R.string.delete_biere_type_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when (state.value.status) {
                DeleteTypeBiereScreenStatus.LOADING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        MeetMyBarLoader()
                    }
                }
                DeleteTypeBiereScreenStatus.ERROR -> {
                    ErrorDialog {
                    }
                }
                else -> {
                    if (state.value.bieresType.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(id = R.string.no_biere_type_available),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.value.bieresType) { biereType ->
                                BiereTypeItem(
                                    biereType = biereType,
                                    onDeleteClick = { viewModel.deleteBarById(biereType.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BiereTypeItem(
    biereType: DrinkTypeModel,
    onDeleteClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = biereType.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.alcohol_degree, biereType.alcoholDegree),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = { showDeleteDialog = true },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_item_description, biereType.name)
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(id = R.string.delete_confirmation_title)) },
            text = { Text(stringResource(id = R.string.delete_confirmation_message, biereType.name)) },
            confirmButton = {
                MeetMyBarButton (
                    text = stringResource(id = R.string.delete)
                ){
                    onDeleteClick()
                    showDeleteDialog = false
                }
            },
            dismissButton = {
                MeetMyBarSecondaryButton (
                    text = stringResource(id = R.string.cancel)
                ) {
                    showDeleteDialog = false
                }
            }
        )
    }
}