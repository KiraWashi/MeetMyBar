package com.example.frontend.presentation.feature.photoNouk

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun PermissionHandler(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val permissions = listOf(
        android.Manifest.permission.CAMERA,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Log.d("PermissionTest", "Toutes les permissions accordées")
        } else {
            Log.d("PermissionTest", "Certaines permissions refusées: $permissionsMap")
            Toast.makeText(
                context,
                "Permissions nécessaires pour utiliser la caméra",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(permissions.toTypedArray())
    }

    content()
}