package com.example.frontend.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MeetMyBarButton(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: ImageVector? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.inversePrimary,
            disabledContentColor = Color.Black.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),

        )
    ) {
        icon?.let {
            Icon(icon, contentDescription = "Add bar")
        }
        text.isNotEmpty().let {
            Text(text)
        }
    }
}

@Preview
@Composable
fun MeetMyBarButtonTextPreview() {
    MeetMyBarButton(
        text = "Continuer",
        onClick = {}
    )
}

@Preview
@Composable
fun MeetMyBarButtonIconPreview() {
    MeetMyBarButton(
        icon = Icons.Filled.Add,
        onClick = {}
    )
}