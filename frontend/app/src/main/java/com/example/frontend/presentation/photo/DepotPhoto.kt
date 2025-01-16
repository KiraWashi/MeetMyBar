package com.example.frontend.presentation.photo

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DepotPhoto() {
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Zone d'affichage de l'image
        capturedImageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Photo capturée",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = "Image URI: $uri",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Component de capture photo
        PhotoCapture { uri ->
            capturedImageUri = uri
            // Afficher un log pour debug
            Log.d("PhotoTest", "Image capturée: $uri")
        }
    }
}