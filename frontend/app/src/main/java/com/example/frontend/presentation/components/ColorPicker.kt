package com.example.frontend.presentation.components

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


object BeerColorTranslator {
    private val displayToDb = mapOf(
        "Blanche" to "biere_blanche",
        "Blonde" to "biere_blonde",
        "Dorée" to "biere_dorée",
        "Ambrée" to "biere_ambre",
        "Rousse" to "biere_rousse",
        "Brune" to "biere_brune",
        "Noire" to "biere_noire",
        "Cuivrée" to "biere_cuivrée",
        "Rubis" to "biere_rouge",
        "Ébène" to "biere_ébène"
    )

    private val dbToDisplay = displayToDb.entries.associate { (k, v) -> v to k }

    fun toDbName(displayName: String): String = displayToDb[displayName] ?: "biere_blonde"
    fun toDisplayName(dbName: String): String = dbToDisplay[dbName] ?: "Blonde"

    val allDisplayNames = displayToDb.keys.toList()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Convertir le nom de la BDD en nom d'affichage si nécessaire
    val displayColor = if (selectedColor.startsWith("biere_")) {
        BeerColorTranslator.toDisplayName(selectedColor)
    } else {
        selectedColor
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = displayColor,
            onValueChange = {},
            readOnly = true,
            label = { Text("Couleur de la bière") },
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(mapBeerColor(BeerColorTranslator.toDbName(displayColor)))
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), CircleShape)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.inversePrimary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
                cursorColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        StyledColorMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            items = BeerColorTranslator.allDisplayNames,
            onItemSelected = { color ->
                // Convertir le nom d'affichage en nom de BDD avant de le passer au callback
                onColorSelected(BeerColorTranslator.toDbName(color))
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
            items.forEach { displayColor ->
                val dbColor = BeerColorTranslator.toDbName(displayColor)
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(mapBeerColor(dbColor))
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = displayColor,
                                color = mapFontOverBeer(dbColor),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    },
                    onClick = { onItemSelected(displayColor) },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}