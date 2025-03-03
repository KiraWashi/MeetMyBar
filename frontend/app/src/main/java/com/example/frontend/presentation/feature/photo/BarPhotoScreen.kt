package com.example.frontend.presentation.feature.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
@Composable
fun BarPhotosScreen(barId: Int) {
    val viewModel = koinViewModel<PhotoViewModel>()
    val photos by viewModel.photos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(barId) {
        viewModel.getPhotosByBar(barId)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                photos.forEach { photo ->
                    PhotoItem(
                        photoId = photo.id,
                        description = photo.description,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoItem(
    photoId: Int,
    description: String,
    viewModel: PhotoViewModel
) {
    val photoState = viewModel.getPhotoState(photoId).collectAsState().value

    Column {
        Text(description + photoId)

        // Chargement de la photo lors de la première composition
        LaunchedEffect(photoId) {
            viewModel.loadPhoto(photoId)
        }

        // Affichage de l'état de chargement ou de l'image
        Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
            if (photoState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            } else {
                photoState.photo?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Photo",
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }
    }
}