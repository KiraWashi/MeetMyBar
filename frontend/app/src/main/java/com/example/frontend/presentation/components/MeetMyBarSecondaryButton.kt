package com.example.frontend.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MeetMyBarSecondaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: ImageVector? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.inversePrimary,
            disabledContentColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary
        ),
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
fun MeetMyBarSecondaryButtonTextPreview() {
    MeetMyBarButton(
        text = "Continuer",
        onClick = {}
    )
}

@Preview
@Composable
fun MeetMyBarSecondaryButtonIconPreview() {
    MeetMyBarButton(
        icon = Icons.Filled.Add,
        onClick = {}
    )
}