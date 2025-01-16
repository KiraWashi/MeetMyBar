package com.example.frontend.presentation.home

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.frontend.R
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.ui.theme.SpritzClairColor
import com.example.frontend.ui.theme.SpritzColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
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
    val locationPermissionState = viewModel.locationPermissionState.collectAsState()
    val userLocation = viewModel.userLocation.collectAsState()

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

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(48.400002, -4.48333), // Position initiale sur Brest
            12f
        )
    }

    LaunchedEffect(userLocation.value) {
        userLocation.value?.let { location ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(location)
            )
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
                    containerColor = SpritzClairColor,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(text = stringResource(id = R.string.home_title))
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.home_settings),
                            tint = Color.Black,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(id = R.string.home_account),
                            tint = Color.Black,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter,
        ) {
            when (locationPermissionState.value) {
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
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                    ) {
                        userLocation.value?.let { location ->
                            Marker(
                                state = MarkerState(position = location),
                                title = stringResource(id = R.string.home_position),
                                icon = bitmapDescriptorFromVector (
                                    LocalContext.current, R.drawable.people
                                ),
                            )
                        }
                        Marker(
                            state = MarkerState(position = LatLng(48.400002, -4.48333)),
                            title = "Sharkpool",
                            icon = bitmapDescriptorFromVector (
                                LocalContext.current, R.drawable.location_bar
                            ),
                            onInfoWindowClick = {
                                navHostController.navigate(Screen.PageBar.route)
                            }
                        )
                    }
                    HomeSearchBar()
                }
            }
        }
    }
}

fun bitmapDescriptorFromVector (
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {

    // récupérer le dessinable réel
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable. intrinsicWidth , drawable. intrinsicHeight )
    val bm = Bitmap.createBitmap(
        drawable. intrinsicWidth ,
        drawable. intrinsicHeight ,
        Bitmap.Config. ARGB_8888
    )

    // le dessiner sur le bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar() {
    var text by remember { mutableStateOf("") }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = SpritzColor,
        backgroundColor = SpritzColor.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        OutlinedTextField(
            modifier = Modifier
                .padding(24.dp)
                .background(color = Color.White, shape = RoundedCornerShape(32.dp)),
            value = text,
            // label = { Text("Rechercher un bar") },
            onValueChange = {
                text = it
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.home_search),
                    tint = SpritzColor,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                focusedBorderColor = SpritzColor,
                unfocusedBorderColor = SpritzColor,
                focusedLabelColor = SpritzColor,
                unfocusedLabelColor = Color.Black,
                cursorColor = SpritzColor,
            ),
            shape = RoundedCornerShape(32.dp),
        )
    }
}