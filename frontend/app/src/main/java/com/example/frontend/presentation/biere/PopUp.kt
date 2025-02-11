package com.example.frontend.presentation.biere

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Popup(onDismiss: () -> Unit, text : String, titre : String) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Retour")
            }
        },
        title = { Text(titre) },
        text = {
            Text(text)
        },
        shape = RoundedCornerShape(16.dp)
    )
}