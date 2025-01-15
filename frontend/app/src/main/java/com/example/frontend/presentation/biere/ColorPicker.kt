package com.example.frontend.presentation.biere

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(selectedColor: String, onColorSelected: (String) -> Unit) {
    val beerColors = listOf("Blanche", "Jaune paille", "Dorée",
        "Ambrée", "Rousse", "Brune", "Noire", "Cuivrée", "Rubis", "Ébène")

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedColor,
            onValueChange = {},
            readOnly = true,
            label = { Text("Couleur de la bière") },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(mapBeerColor(selectedColor))
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), CircleShape)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        StyledColorMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            items = beerColors,
            onItemSelected = { color ->
                onColorSelected(color)
                expanded = false
            }
        )
    }
}

@Composable
fun StyledColorMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .animateContentSize()
            .padding(top = 4.dp),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            items.forEach { color ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(mapBeerColor(color))
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = color,
                                color = mapFontOverBeer(color),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    },
                    onClick = { onItemSelected(color) },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}