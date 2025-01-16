package com.example.frontend.presentation.photo
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PhotoCapture(
onImageCaptured: (Uri?) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher pour la galerie
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        onImageCaptured(uri)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Afficher l'image capturée
        imageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Captured image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Bouton pour choisir depuis la galerie
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Choisir depuis la galerie")
        }
    }
}