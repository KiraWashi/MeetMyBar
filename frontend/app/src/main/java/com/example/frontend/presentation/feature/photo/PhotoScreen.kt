package com.example.frontend.presentation.feature.photo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
@Composable
fun PhotoScreen() {
    val viewModel = koinViewModel<PhotoViewModel>()
    val state = viewModel.photoViewModelState.collectAsState().value
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.postPhoto(
                uri = it,
                context = context,
                mainPhoto = true,
                description = "Test"
            )
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.getPhotoById(id = 6)
            }) {
                Text(text = "Afficher la photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                enabled = !state.isUploading
            ) {
                Text(text = "Choisir une photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.photo?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Photo",
                    modifier = Modifier.size(200.dp)
                )
            }

            // Afficher l'état d'upload
            if (state.isUploading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                Text("Upload en cours...")
            }

            state.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (state.uploadSuccess) {
                Text(
                    text = "Photo téléchargée avec succès !",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    // Réinitialiser l'état d'upload quand l'écran est fermé
    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.resetUploadState()
        }
    }
}