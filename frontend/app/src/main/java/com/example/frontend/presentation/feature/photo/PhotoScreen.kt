package com.example.frontend.presentation.feature.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoScreen() {
    val viewModel = koinViewModel<PhotoViewModel>()
    val photoViewModelState = viewModel.photoViewModelState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Button(onClick = {
                viewModel.getPhotoById(id = 6)
            }) {
                Text(text = "Afficher la photo")
            }
            photoViewModelState.value.photo?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Photo",
                )
            }
        }
    }
}