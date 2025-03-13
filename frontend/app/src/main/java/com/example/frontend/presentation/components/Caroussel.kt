package com.example.frontend.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.graphics.BitmapFactory
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.asImageBitmap
import com.example.frontend.R
import com.example.frontend.presentation.feature.photo.PhotoViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun Caroussel(
    barId: Int,
) {
    val viewModel = koinViewModel<PhotoViewModel>()
    val photos by viewModel.photos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(barId) {
        viewModel.getPhotosByBar(barId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (photos.isNotEmpty()) {
            // Utilisation des photos du ViewModel au lieu des images statiques
            val pagerState = rememberPagerState(pageCount = { photos.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Hauteur fixe ou ajustable selon vos besoins
            ) { page ->
                // PhotoItem pour chaque page
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PhotoItem(
                        photoId = photos[page].id,
                        description = photos[page].description,
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Indicateurs
            Row(
                Modifier
                    .padding(top = 16.dp)
                    .height(30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(photos.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val size by animateFloatAsState(
                        targetValue = if (isSelected) 12f else 8f,
                        label = "dot size"
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(size.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        } else {
            // Afficher un message si aucune photo n'est disponible
            Text(
                text = "Aucune photo disponible",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun PhotoItem(
    photoId: Int,
    description: String,
    viewModel: PhotoViewModel,
    modifier: Modifier = Modifier
) {
    val photoState = viewModel.getPhotoState(photoId).collectAsState().value

    // Chargement de la photo lors de la première composition
    LaunchedEffect(photoId) {
        viewModel.loadPhoto(photoId)
    }

    // Affichage de l'état de chargement ou de l'image
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (photoState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                photoState.photo?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = description,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}