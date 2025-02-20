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
import com.example.frontend.R
import kotlin.math.roundToInt

@Composable
fun Caroussel() {
    val images = listOf(
        R.drawable.images,
        R.drawable.images1,
        R.drawable.images2,
        R.drawable.images3,
        R.drawable.images4
    )

    val context = LocalContext.current
    val imageSizes = remember {
        images.map { resourceId ->
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeResource(context.resources, resourceId, options)
            Pair(options.outWidth, options.outHeight)
        }
    }

    val pagerState = rememberPagerState(pageCount = { images.size })

    // Utiliser 90% de la largeur de l'écran
    val screenWidth = (LocalContext.current.resources.displayMetrics.widthPixels * 0.9).toInt()

    val currentImageHeight = remember(pagerState.currentPage) {
        val (width, height) = imageSizes[pagerState.currentPage]
        // Calculer la hauteur proportionnelle en gardant le ratio d'aspect
        val scaledHeight = (screenWidth * height.toFloat() / width.toFloat()).roundToInt()
        // Convertir en dp et ajouter un facteur multiplicateur pour agrandir
        (scaledHeight / context.resources.displayMetrics.density * 1.2).dp // Facteur 1.2 pour 20% plus grand
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Ajouter du padding vertical
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(currentImageHeight)
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = null,
                contentScale = ContentScale.FillHeight, // Changé en FillHeight pour maximiser la taille
                modifier = Modifier
                    .padding(horizontal = 8.dp) // Réduit le padding horizontal
                    .fillMaxSize() // Utilise tout l'espace disponible
            )
        }

        // Indicateurs
        Row(
            Modifier
                .padding(top = 16.dp) // Ajouter du padding au-dessus des indicateurs
                .height(30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(images.size) { index ->
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
    }
}