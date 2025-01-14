package com.example.frontend.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.frontend.ui.theme.SpritzClairColor
import com.example.frontend.ui.theme.SpritzColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpritzClairColor,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("MeetMyBar")
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Account",
                            tint = Color.Black,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        HomeScreen(
            modifier = Modifier.padding(innerPadding)
        )
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(48.400002, -4.48333),
                        12f
                    )
                }
            ) {}
            HomeSearchBar()
        }
    }
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
                    contentDescription = "Search",
                    tint = SpritzColor,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
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