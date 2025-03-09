package com.example.frontend.presentation.feature.deletebar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontend.R
import com.example.frontend.domain.model.BarModel
import com.example.frontend.presentation.components.Error
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.Loader
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.components.MeetMyBarSecondaryButton
import com.example.frontend.presentation.feature.home.HomeStatus
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBarScreen(
    navHostController: NavHostController,
) {
    val viewModel = koinViewModel<DeleteBarViewModel>()
    val state = viewModel.deleteBarViewModelState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfirmDialog by remember { mutableStateOf(false) }

    state.value.successMessage?.let { message ->
        LaunchedEffect(message) {
            scope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.clearSuccessMessage()
            }
        }
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
                    Text(text = stringResource(id = R.string.delete_bar_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.value.selectedBars.isNotEmpty(),
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                MeetMyBarButton(
                    text = "Supprimer",
                    icon = Icons.Filled.Delete
                ) {
                    showConfirmDialog = true
                }
                /*ExtendedFloatingActionButton(
                    text = { Text("Supprimer") },
                    icon = { Icon(Icons.Filled.Delete, contentDescription = "Supprimer") },
                    onClick = { showConfirmDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondary,
                )*/
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state.value.deleteBarScreenStatus) {
                HomeStatus.LOADING -> {
                    Loader(modifier = Modifier.align(Alignment.Center))
                }
                HomeStatus.ERROR -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Error()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Impossible de charger la liste des bars",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getBars() }) {
                            Text("Réessayer")
                        }
                    }
                }
                HomeStatus.SUCCESS -> {
                    if (state.value.bars.isNullOrEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Aucun bar disponible",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            item {
                                Text(
                                    text = "Sélectionnez les bars à supprimer",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            }

                            items(state.value.bars ?: emptyList()) { bar ->
                                BarItem(
                                    bar = bar,
                                    isSelected = state.value.selectedBars.contains(bar.id),
                                    onToggle = { viewModel.toggleBarSelection(bar.id) },
                                    onDelete = { viewModel.deleteBarById(bar.id) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            item {
                                Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }
                }
            }

            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text("Confirmation") },
                    text = {
                        Text(
                            "Êtes-vous sûr de vouloir supprimer ${state.value.selectedBars.size} bar(s) ?\n\nCette action est irréversible."
                        )
                    },
                    confirmButton = {
                        MeetMyBarButton (
                            text = "Supprimer"
                        ){
                            viewModel.deleteSelectedBars()
                            showConfirmDialog = false
                        }
                        /*Button(
                            onClick = {
                                viewModel.deleteSelectedBars()
                                showConfirmDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text("Supprimer")
                        }*/
                    },
                    dismissButton = {
                        MeetMyBarSecondaryButton(
                            text = "Annuler"
                        ) {
                            showConfirmDialog = false
                        }
                        /*Button(
                            onClick = { showConfirmDialog = false }
                        ) {
                            Text("Annuler")
                        }*/
                    }
                )
            }

            if (state.value.showErrorDialog) {
                ErrorDialog(
                    onDismissDialog = { viewModel.hideDialog() }
                )
            }
        }
    }
}

@Composable
fun BarItem(
    bar: BarModel,
    isSelected: Boolean,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.tertiary
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.secondary
                        else Color.Transparent
                    )
                    .border(
                        width = 2.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Sélectionné",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Informations du bar
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = bar.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "${bar.address}, ${bar.postalCode} ${bar.city}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Bouton de suppression individuelle
            IconButton(
                onClick = { showDeleteConfirm = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Supprimer",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

    // Dialogue de confirmation de suppression individuelle
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Supprimer ${bar.name}") },
            text = { Text("Êtes-vous sûr de vouloir supprimer ce bar ?") },
            confirmButton = {
                MeetMyBarButton (
                    text = "Supprimer"
                ){
                    onDelete()
                    showDeleteConfirm = false
                }
                /*Button(
                    onClick = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Supprimer")
                }*/
            },
            dismissButton = {
                MeetMyBarSecondaryButton(
                    text = "Annuler"
                ) {
                    showDeleteConfirm = false
                }
                /*Button(
                    onClick = { showDeleteConfirm = false }
                ) {
                    Text("Annuler")
                }*/
            }
        )
    }
}