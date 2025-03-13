package com.example.frontend.presentation.feature.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.frontend.R
import com.example.frontend.domain.model.BarModel
import com.example.frontend.presentation.components.ErrorDialog
import com.example.frontend.presentation.components.MeetMyBarLoader
import com.example.frontend.presentation.components.MeetMyBarButton
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.ui.theme.SpritzColorLight
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val context = LocalContext.current
    val homeViewModelState = viewModel.homeViewModelState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBars()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.updatePermissionState(LocalisationState.Granted)
            viewModel.getCurrentLocation(context)
        } else {
            viewModel.updatePermissionState(LocalisationState.Denied)
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                viewModel.updatePermissionState(LocalisationState.Granted)
                viewModel.getCurrentLocation(context)
            }

            else -> {
                viewModel.updatePermissionState(LocalisationState.RequiresPermission)
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
                    Text(text = stringResource(id = R.string.home_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(route = Screen.SettingsScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.home_settings),
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                },
                actions = {
                    // TODO
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = stringResource(id = R.string.home_account),
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter,
        ) {
            when (homeViewModelState.value.locationState) {
                LocalisationState.Loading -> {
                    CircularProgressIndicator()
                }

                LocalisationState.Denied -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(id = R.string.home_localisation_message))
                        Button(
                            onClick = {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        ) {
                            Text(text = stringResource(id = R.string.home_activate_localisation))
                        }
                    }
                }

                else -> {
                    when (homeViewModelState.value.homeStatus) {
                        HomeStatus.LOADING ->
                            MeetMyBarLoader(modifier = Modifier.align(Alignment.Center))

                        HomeStatus.ERROR, HomeStatus.SUCCESS -> {
                            HomeMap(
                                userLocation = homeViewModelState.value.userLocation,
                                barsSearch = homeViewModelState.value.barsSearch,
                                selectedBar = homeViewModelState.value.selectedBar,
                                onClickBar = { bar ->
                                    viewModel.onClickBar(bar)
                                },
                                onClickSeeMore = { barId ->
                                    navHostController.navigate(
                                        Screen.PageBar.createRoute(barId)
                                    )
                                },
                                isOpen = { bar ->
                                    viewModel.isOpen(bar)
                                },
                                isAppInDarkTheme = homeViewModelState.value.isAppInDarkTheme
                            )
                            HomeSearchBar(
                                onSearchChange = { viewModel.onSearchChange(it) },
                            )
                            FloatingActionButton(
                                onClick = {
                                    navHostController.navigate(Screen.EditBarMenuScreen.route)
                                },
                                modifier = Modifier
                                    .padding(24.dp)
                                    .align(Alignment.BottomStart),
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.inversePrimary,
                            ) {
                                Icon(Icons.Filled.Edit, "Add bar")
                            }
                            if (homeViewModelState.value.showDialog) {
                                ErrorDialog(
                                    onDismissDialog = { viewModel.hideDialog() }
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
fun HomeMap(
    userLocation: LatLng?,
    barsSearch: List<BarModel>?,
    selectedBar: BarModel?,
    onClickBar: (BarModel?) -> Unit,
    onClickSeeMore: (Int) -> Unit,
    isOpen: (BarModel) -> Boolean,
    isAppInDarkTheme: Boolean,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(48.400002, -4.48333),
            14f
        )
    }

    LaunchedEffect(userLocation) {
        userLocation?.let { location ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(location)
            )
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true,
        ),
    ) {
        barsSearch?.forEach { bar ->
            bar.latitude?.let { latitude ->
                bar.longitude?.let { longitude ->
                    if (selectedBar?.name == bar.name) {
                        MarkerInfoWindow(
                            state = MarkerState(position = LatLng(latitude, longitude)),
                            icon = bitmapDescriptorFromVector(
                                LocalContext.current, R.drawable.location_bar
                            ),
                            content = {
                                MapInfoWindow(
                                    bar = bar,
                                    onClickSeeMore = { barId ->
                                        onClickSeeMore(barId)
                                    },
                                    isOpen = { bar ->
                                        isOpen(bar)
                                    }
                                )
                            },
                            onInfoWindowClick = {
                                onClickSeeMore(bar.id)
                            },
                        )
                    } else {
                        Marker(
                            state = MarkerState(position = LatLng(latitude, longitude)),
                            icon = bitmapDescriptorFromVector(
                                LocalContext.current, R.drawable.location_bar
                            ),
                            onClick = {
                                onClickBar(bar)
                                true
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MapInfoWindow(
    bar: BarModel,
    isOpen: (BarModel) -> Boolean,
    onClickSeeMore: (Int) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = bar.name,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bar.address + ", " + bar.postalCode + " " + bar.city,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row {
                Icon(
                    imageVector = if (isOpen(bar)) Icons.Filled.Check else Icons.Filled.Close,
                    tint = if (isOpen(bar)) Color.Green else Color.Red,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = if (isOpen(bar)) R.string.home_open else R.string.home_close)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            MeetMyBarButton(
                text = stringResource(id = R.string.home_see_more_button),
                onClick = {
                    onClickSeeMore(bar.id)
                }
            )
        }
    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar(
    onSearchChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = SpritzColorLight,
        backgroundColor = SpritzColorLight.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        OutlinedTextField(
            modifier = Modifier
                .padding(24.dp)
                .background(color = Color.White, shape = RoundedCornerShape(32.dp)),
            value = text,
            onValueChange = {
                text = it
            },
            trailingIcon = {
                IconButton(onClick = { onSearchChange(text) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.home_search),
                        tint = SpritzColorLight,
                    )
                }

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
                cursorColor = MaterialTheme.colorScheme.secondary,
            ),
            shape = RoundedCornerShape(32.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBottomSheet(
    bar: BarModel,
    showBottomSheet: Boolean,
    onDismissBottomSheet: () -> Unit,
    onClickShowMore: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissBottomSheet()
            },
            sheetState = sheetState
        ) {
            Text(
                text = bar.name
            )
            MeetMyBarButton(
                text = "Voir plus",
                onClick = {
                    onClickShowMore()
                }
            )
        }
    }
}