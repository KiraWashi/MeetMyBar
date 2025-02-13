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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val photoViewModelState = viewModel.photoViewModelState.collectAsState()

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

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Choisir une photo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            photoViewModelState.value.photo?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Photo",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}
